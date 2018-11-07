package com.example.bats.homefoodie.database.dishDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.ui.detail.DishDetailActivity;

import java.util.List;

/**
 * {@link Dao} which provides an api for all data operations with the {@link HomeFoodieDatabase}
 */
@Dao
public interface DishDao {

    /**
     * Selects all dishes. the LiveData will be kept in sync with the database.
     * so that it will automatically notify observers when the
     * * values in the table change.
     * @return users
     */
    @Query("SELECT * FROM dish ")
    LiveData<List<DishWithIngredients>> getAllDisheswithIngredients();


    @Query("SELECT * FROM ingredient WHERE dishId = :dishId")
    List<Ingredient> getIngredientsForDish(int dishId);


    @Transaction
    @Query("SELECT * FROM dish WHERE userId == :userId")
    LiveData<List<DishEntry>> getDishForUser(int userId);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDish(DishEntry dish);


    @Query("DELETE FROM dish")
    void deleteRepo();


    //----------------------------------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDish(DishEntry dish);

    @Insert
    void insertIngredientsList(List<Ingredient> ingredients);

    default void insertDishWithIngredients(DishEntry dish ){
        List<Ingredient> ingredients = getIngredientsForDish(dish.getId());
        for (int i = 0; i < ingredients.size(); i++) {
            ingredients.get(i).setDishId(dish.getId());
        }
        insertIngredientsList(ingredients);
        insertDish(dish);
    }


    @Query(" SELECT * from dish WHERE userId = :id")
    DishEntry getDishForAUser(int id);


    default DishEntry getDishWithIngredient(int id) {
        DishEntry dish = getDishForAUser(id);
        List<Ingredient> ingredients = getIngredientsForDish(dish.getId());
        dish.setIngredientList(ingredients);
        return dish;
    }
    //----------------------------------------------------------------------------------------------



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInset(DishEntry... dish);





//    @Query("SELECT * FROM ingredient WHERE dishId =:userId")
//    public abstract List<Ingredient> getIngredientList(int userId);








}
