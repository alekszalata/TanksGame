package KeyBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Input extends JComponent{
     private boolean[] keyboard;


     public Input(){
      keyboard = new boolean[255];
      for (int i = 0 ; i <keyboard.length ; i++) {   //для каждой кнопки
          final int  KEY_CODE = i;

          getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2); //нажатие кнопок только когда окно в фокусе , i = значение копки в коде , false = нажата , i*2 "название" для действия
          getActionMap().put(i * 2, new AbstractAction() {
              public void actionPerformed(ActionEvent e) {
                  keyboard[KEY_CODE] = true;
              }
          });

          getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1); //i * 2 +1 т.к уже для отжатой кнопки
          getActionMap().put(i * 2 + 1, new AbstractAction() {
              public void actionPerformed(ActionEvent e) {
                  keyboard[KEY_CODE] = false;
              }
          });
      }
     }


     public boolean getKey(int keyCode){
         return keyboard[keyCode];
    }


}
