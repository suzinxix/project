package bee;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProfileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(Profile.User... users);

    @Update
    public void updateUsers(Profile.User... users);

    @Delete
    public void deleteUsers(Profile.User... users);


    @Query("SELECT * FROM User")
    public Profile.User[] loadAllUsers();
}
