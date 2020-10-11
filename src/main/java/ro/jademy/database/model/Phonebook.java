package ro.jademy.database.model;

import java.util.List;

public class Phonebook {
    private List<Contact> contactList;

    public void backup(){}
        //AlarmManager so I can do a backup every 24h. (Runs even the app is closed.

        /*
        This will run only when the app is on!

        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask () {
            @Override
            public void run () {
                // your code
                callSpeak().execute() // Your method
            }
        };

        myTimer.scheduleAtFixedRate(myTask , 0l, 5 * (60*1000)); // Runs every 5 mins
    */

}
