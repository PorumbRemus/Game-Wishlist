package Test;
import com.wishlist.wishlist.Game;
import com.wishlist.wishlist.GameList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GameListTest {
    @Test
    public void testAdd(){
        GameList testList=new GameList();
        Game game= new Game(0,"Grand Theft Auto 4",2008,"Rockstar Games","Action",50);
        testList.addGame(game);
        assertEquals("Grand Theft Auto 4",testList.Games.get(testList.Games.size()-1).name);
        assertEquals(2008,testList.Games.get(testList.Games.size()-1).year);
        assertEquals("Rockstar Games",testList.Games.get(testList.Games.size()-1).creator);
        assertEquals("Action",testList.Games.get(testList.Games.size()-1).genre);
        assertEquals(50,testList.Games.get(testList.Games.size()-1).price);

    }


}
