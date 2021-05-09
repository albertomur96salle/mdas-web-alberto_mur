package com.ccm.pokemon.pokemon.infrastructure.repositories;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.valueObjects.Name;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonType;
import com.ccm.pokemon.pokemon.infrastructure.databaseentities.PokemonDB;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.eventlisteners.NewFavouritePokemonListener;
import com.ccm.pokemon.pokemon.infrastructure.externalclients.PokemonApiClient;
import com.ccm.pokemon.pokemon.infrastructure.parsers.JsonToPokemonParser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.simple.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Named("MySQL")
public class MySqlPokemonRepository implements PokemonRepository {
    private static SessionFactory factory;
    private Logger logger = Logger.getLogger(MySqlPokemonRepository.class.getName());

    @Inject
    PokemonApiClient pokemonApiClient;

    @Inject
    JsonToPokemonParser jsonToPokemonParser;

    public MySqlPokemonRepository() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public Pokemon find(PokemonId pokemonId) throws PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {
        Session session = factory.openSession();
        PokemonDB tmp = session.get(PokemonDB.class, pokemonId.getPokemonId());

        if (tmp == null) {
            try {
                JSONObject pokeApiResult = pokemonApiClient.find(pokemonId);

                return jsonToPokemonParser.castJsonToPokemon(pokeApiResult);
            } catch (PokemonNotFoundException | TimeoutException | NetworkConnectionException | UnknownException e) {
                throw e;
            }
        }

        Pokemon result = new Pokemon(new PokemonId(tmp.getId()), new Name(tmp.getName()));
        for (int i=0; i<tmp.getCounter(); i++) {
            result.incrementCounter();
        }
        String[] types = tmp.getTypes().split(",");
        for (String type: types) {
            result.addPokemonType(new PokemonType(type));
        }

        return result;
    }

    @Override
    public void save(Pokemon pokemon) {
        Session session = factory.openSession();
        Transaction tx = null;

        PokemonDB pokemonDB = new PokemonDB();
        pokemonDB.setId(pokemon.getPokemonId().getPokemonId());
        pokemonDB.setName(pokemon.getName().getName());
        pokemonDB.setCounter(pokemon.getFavouriteCounter().getFavouriteCounter());
        String types = pokemon.getPokemonTypes().getPokemonTypes().toString()
            .replaceAll("^\\[", "")
            .replaceAll("]$", "")
            .replaceAll(" ", "");
        pokemonDB.setTypes(types);
        System.out.println(pokemonDB.getTypes());
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(pokemonDB);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
