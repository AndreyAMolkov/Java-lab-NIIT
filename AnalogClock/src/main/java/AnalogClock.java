
    import java.awt.*;
import java.awt.event.*;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;
    import java.time.LocalTime;
    import java.util.*;
    import javax.imageio.ImageIO;
    import javax.swing.*;

    public class AnalogClock extends JComponent
            implements Runnable
    {

        public AnalogClock()
        {
            (new Thread(this)).start();
        }

        public void run()
        {
            try {
                for(;;)
                {
                    Thread.sleep(500);
                    repaint();
                }
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }

        public void paint(Graphics graphics)
        {
            super.paint(graphics);

            // calculate the time values
            String tDate = (new Date()).toString();
            LocalTime lt=LocalTime.now();

            long minute=lt.getMinute();
            long sec = lt.getSecond();
            long hour = lt.getHour();

            // calculate the clock hand angles
            double sec_angle = -Math.PI/2+sec*6.0*Math.PI/180;;
            double min_angle = -Math.PI/2+6.0*Math.PI/180*(minute+sec/60.0);;
            double hour_angle = -Math.PI/2+30.0*Math.PI/180*(hour+minute/60.0);


            // Draw the hands

            Graphics2D g = (Graphics2D) graphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            size = getSize(size);
            insets = getInsets(insets);
            int radius = Math.min(size.width - insets.left - insets.top, size.height - insets.top - insets.bottom) / 2;

            g.translate((double) size.width / 2D, (double) size.height / 2D);

            // draw the seconds
            g.setColor(Color.red);
            g.setStroke(SEC_STROKE);
            g.rotate(sec_angle);
            g.drawLine(-40, 0, 280 -6, 0);
            g.rotate(-sec_angle);

            //draw the minutes

            g.setColor(Color.black);
            g.setStroke(MIN_STROKE);
            g.rotate(min_angle);
            BufferedImage imageBigHand = null;
            try {
                imageBigHand = ImageIO.read((AnalogClock.class.getResourceAsStream("bigHandOfClock.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(imageBigHand, -20 , -20 , this);
            g.rotate(-min_angle);

            // draw the hours
            g.setColor(Color.black);
            g.setStroke(HOUR_STROKE);
           g.rotate(hour_angle);
            BufferedImage imageSmallHand = null;
            try {
                imageSmallHand = ImageIO.read((AnalogClock.class.getResourceAsStream("smallHandOfClock.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(imageSmallHand, -20 , -17 , this);
            g.rotate(-hour_angle);

            // draw the perimeter
            BufferedImage imagePerimetr = null;
            try {
                imagePerimetr = ImageIO.read((AnalogClock.class.getResourceAsStream("clockFace.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(imagePerimetr, -280 , -280 , this);
        }

        private static long MILLIS_IN_MINUTE = 60L * 1000L;
        private static long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60L;
        private static long MILLIS_IN_HALF_DAY = MILLIS_IN_HOUR * 12L;


        private static Stroke SEC_STROKE = new BasicStroke();
        private static Stroke MIN_STROKE =
                new BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        private static Stroke HOUR_STROKE = MIN_STROKE;

        private Dimension size = null;
        private Insets insets = new Insets(0, 0, 0, 0);

        public static void main(String[] args)
        {
            JFrame f = new JFrame("Clock");
            AnalogClock clock = new AnalogClock();
            f.getContentPane().add(clock);
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            f.setSize(600,600);

            f.show();
        }

    }

