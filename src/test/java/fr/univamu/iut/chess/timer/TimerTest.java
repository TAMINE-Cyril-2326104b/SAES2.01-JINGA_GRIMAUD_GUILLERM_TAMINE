package fr.univamu.iut.chess.timer;

import fr.univamu.iut.chess.controllers.ChessPlayerGameController;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimerTest {

    private ChessPlayerGameController controller;
    private Label timeLabelWhite;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.initialize(); // Initialiser le contrôleur (y compris les timers)
        timeLabelWhite = controller.timeLabelWhite; // Récupérer le label depuis le contrôleur
    }

    @Test
    public void testTimer() throws InterruptedException {
        assertNotNull(timeLabelWhite); // Assurez-vous que le label n'est pas null
        // Vérifier que le timer blanc est initialisé à 10 minutes
        assertEquals("10:00", timeLabelWhite.getText());

        // Attendre quelques secondes pour simuler le passage du temps
        Thread.sleep(3000); // Attendre 3 secondes

        // Vérifier que le temps blanc a été décrémenté de 3 secondes
        assertEquals("09:57", timeLabelWhite.getText());
    }
}
