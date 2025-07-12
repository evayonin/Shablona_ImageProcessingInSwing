package com.example;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel { // מימוש בWindow
  private BufferedImage originalImage; // תמיד חייב לשמור עותק של התמונה המקורית
  private BufferedImage currentImage; // המשתנה שבו נשמור את התמונה המקורית ששינינו
  private double scale;

  public ImagePanel(int x, int y, int width, int height) {
    this.setLayout(null);
    this.setBounds(x, y, width, height);
    // this.scale = 1;
    createMyImage();

    // הרבה יותר כדאי פשוט לעשות עם אקשן ליסנר כי זה תמיד מגיב לאינטרקציה עם הכפתור
    // בלי שימוש בעוד מתודות:

    // כפתור מראה אופקית
    JButton hMirror = new JButton("H mirror");
    hMirror.setBounds(10, 10, 100, 30); // מיקום בתוך הפאנל
    this.add(hMirror);
    hMirror.addActionListener(e -> horizontalMirror());

    // כפתור מראה אנכית
    JButton vMirror = new JButton("V mirror");
    vMirror.setBounds(120, 10, 100, 30);
    this.add(vMirror);
    vMirror.addActionListener(e -> verticalMirror());
  }

  private void createMyImage() {
    try {
      this.originalImage = ImageIO.read(new File("src/demo/src/main/resources/sunset-1373171.jpg")); // copy relative
                                                                                                     // path
      // this.currentImage = this.originalImage; // קורנט קיבל את אותה כתובת בזיכרון
      // כמו אוריג׳ינל ומצביע על אותה תמונה
      // בדיוק ולכן זה לא טוב
      // אנחנו רוצים לעשות דיפ-קופי ז״א להעתיק את אותה תמונה לכתובת אחרת בזיכרון
      // ככה שהשינויים ייראו רק בקורנט ולא באוריגינל
      // ניצור פונקציה שתעתיק כל פיקסל
      this.currentImage = deepCopy();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private BufferedImage deepCopy() {
    // כשיוצרים האפרד אימג׳ חדש צריך לשלוח רוחב גובה וסוג(סיומת)
    BufferedImage copy = new BufferedImage(this.originalImage.getWidth(), this.originalImage.getHeight(),
        this.originalImage.getType());
    for (int y = 0; y < copy.getHeight(); y++) { // שורות(גובה)
      for (int x = 0; x < copy.getWidth(); x++) { // עמודות(רוחב)
        int imageRgb = this.originalImage.getRGB(x, y);
        copy.setRGB(x, y, imageRgb);
      }
    }
    return copy;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g); // חייב אם אנחנו כותבים מחלקה חדשה ושרוצים להוסיף משהו
    if (this.currentImage != null) {
      Graphics2D g2d = (Graphics2D) g; // casting

      g2d.drawImage(this.currentImage, 0, 0, getWidth(), getHeight(), this);
      // שיתאים את גודל התמונה לפאנל

    }
  }

  public void changeImage() {
    int width = this.currentImage.getWidth();
    int height = this.currentImage.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // עבור כל פיקסל:

        // int originalRGB = this.currentImage.getRGB(x, y); // הפיקסל המקורי
        // int newRGB; //

        this.currentImage.setRGB(x, y, newRGB); // ישים במיקום המקורי את הצבע ההפוך
        this.currentImage.setRGB(x, oppositeY, originalRGB); // ישים במיקום ההפוך את הצבע המקורי
      }
    }
    repaint();
  }

  public void horizontalMirror() {
    int width = this.currentImage.getWidth();
    int height = this.currentImage.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width / 2; x++) { // נחליף את הערכים המתאימים עד החצי (כמו פלינדרום) ברוחב
        // עבור כל פיקסל:
        int oppositeX = width - 1 - x; // בצד השני בהתאם

        int originalRGB = currentImage.getRGB(x, y); // הפיקסל המקורי
        int oppositeRGB = this.currentImage.getRGB(oppositeX, y); // הפיקסל באותה שורה בצד השני

        currentImage.setRGB(x, y, oppositeRGB); // ישים במיקום המקורי את הצבע ההפוך
        currentImage.setRGB(oppositeX, y, originalRGB); // ישים במיקום ההפוך את הצבע המקורי
      }
    }
    repaint();
  }
}