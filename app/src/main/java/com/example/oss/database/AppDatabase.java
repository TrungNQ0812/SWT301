package com.example.oss.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.oss.entity.*;
import com.example.oss.dao.*;
import com.example.oss.util.Converters;

@Database(entities = {
        Category.class,
        User.class,
        Product.class,
        Review.class,
        Wishlist.class,
        Address.class,
        Order.class,
        OrderItem.class,
        Cart.class
}, version = 3, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    // Abstract methods cho các DAO
    public abstract CategoryDao categoryDao();

    public abstract UserDao userDao();

    public abstract ProductDao productDao();

    public abstract ReviewDao reviewDao();

    public abstract WishlistDao wishlistDao();

    public abstract AddressDao addressDao();

    public abstract OrderDao orderDao();

    public abstract OrderItemDao orderItemDao();

    public abstract CartDao cartDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "oss_database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}