package lk.ijse.studentsmanagement.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TimeDate {
    public static void localDateAndTime(Label lblDate, Label lblTime) {

        lblDate.setText("  "+new SimpleDateFormat("dd : MM : 20yy ")
                .format(
                        new Date()
                ));


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> lblTime.setText("  "+new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public static void setGreeting(Label lblGreetings, ImageView wishImageView){
        Calendar calender = Calendar.getInstance();
        LocalDate now = LocalDate.now();
        calender.setTime(new Date());
        int hours = calender.get(Calendar.HOUR_OF_DAY);
        int min = calender.get(Calendar.MINUTE);
        if (hours >= 1 && hours < 12) {
            lblGreetings.setText("Good Morning...");
            wishImageView.setImage(new Image("/morning.png"));
        } else if (hours >= 12 && hours <= 16) {
            lblGreetings.setText("Good Afternoon...");
            wishImageView.setImage(new Image("/afternoon.png"));
        }else {
            lblGreetings.setText("Good Evening...");
            wishImageView.setImage(new Image("/night.png"));
        }

    }
}
