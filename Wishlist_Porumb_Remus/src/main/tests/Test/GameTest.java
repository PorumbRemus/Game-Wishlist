package Test;
import static org.junit.jupiter.api.Assertions.*;
import org.testng.annotations.Test;
import com.wishlist.wishlist.Game;
public class GameTest {
    @Test
    public void TestGameConstructor(){
        Game testGame=new Game(0,"Grand Theft Auto 4",2008, "Rockstar Games","Action",50);
        assertEquals("Grand Theft Auto 4",testGame.name);
        assertEquals(2008,testGame.year);
        assertEquals("Rockstar Games",testGame.creator);
        assertEquals("Action",testGame.genre);
        assertEquals(50,testGame.price);

    }

}
