package accountAppUtil;

import accountAppCon.AccountController;
import accountAppCon.CallController;
import accountAppCon.MmsController;
import accountAppCon.SmsController;
import accountAppMod.Account;
import accountAppMod.Call;
import accountAppMod.Mms;
import accountAppMod.Sms;
import accountAppMsg.MessageListener1;
import accountAppMsg.MessageListener2;
import accountAppMsg.MessageTransmiter1;
import accountAppMsg.MessageTransmiter2;
import accountAppView.MainFormController;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Deamon
 */
public class ChargingSystem extends Thread {

    private Integer accRan;

    private AccountController accCon;
    private Account acc;
    private Account acc2;
    private ArrayList listOfAccounts;
    private CallController callCon;
    private static SmsController smsCon;
    private static MmsController mmsCon;
    public MainFormController mainForm;

    public MessageListener1 msgLis1;
    public MessageListener2 msgLis2;

    public MessageTransmiter1 msgTrn1 = null;
    public MessageTransmiter2 msgTrn2 = null;

    private BigDecimal charged;
    private BigDecimal newBalance;
    private static Long timeToRun;
    private BigDecimal timeInMillSec;
    private BigDecimal timeInSec;
    private final double toPay = 0;
    private static BigDecimal tempBalance;
    private static Long currentTime;
    private static int numberOfSms;
    private static int numberOfMms;
    private int compare;

    public Button stopSms;
     public TextArea textChatUsr1;
     public TextArea textChatUsr2;
    
    private volatile boolean execute;
    private PrintStream standardOut;

    private final String newLine = "\n";
    private int choice;
    //HRK
    private final double oneSecondHrk = 0.02;
    private final double oneSmsHrk = 0.15;
    private final double oneMmsHrk = 0.25;

    //EURO
    private final double oneSecondEuro = 0.002;
    private final double oneSmsEuro = 0.10;
    private final double oneMmsEuro = 0.15;

    public ChargingSystem() {
        acc = new Account();
        accCon = new AccountController();

    }

    public void selectRandomAccount() {

        try {
            listOfAccounts = accCon.getIds();
        } catch (Exception e) {
            listOfAccounts = null;
            System.out.println("Not able to retrive ids");
        }

        try {
            accRan = randomId(listOfAccounts);
            setAccRan(accRan);
        } catch (Exception e) {
            System.out.println("Not able to get random account");
        }

    }

    public void useAccount(TextArea text, TextArea textChatUsr1,TextArea textChatUsr2, Pane user1, Pane user2, Button stopSms) {
        acc = new Account();
        acc2 = new Account();

        try {

            selectRandomAccount();
            acc = accCon.getAccountById(getAccRan());
            selectRandomAccount();
            acc2 = accCon.getAccountById(getAccRan());

            /*if (acc.getKey() == (acc2.getKey())) {
                acc = null;
                acc2 = null;
                text.appendText("Not possible to work with same account " + newLine);
                return;
            }*/
        } catch (Exception e) {
            System.out.println("Not able to retrive account's from database");
            acc = null;
            acc2 = null;
        }

        text.appendText("Account selected " + acc.getKey() + newLine);
        text.appendText("Account selected " + acc2.getKey() + newLine);
        randomAccountAction(acc, acc2, text, textChatUsr1, textChatUsr2, user1, user2, stopSms);

    }

    public void call(Account acc, TextArea text) {
        text.clear();

        tempBalance = acc.getInitbalance();

        BigDecimal k = new BigDecimal(1000);
        timeToRun = GenerateRandomCallTime();
        timeInMillSec = new BigDecimal(timeToRun);
        timeInSec = timeInMillSec.divide(k);
        text.appendText("Call will last for " + timeInSec + " seconds" + newLine);
        currentTime = System.currentTimeMillis();
        BigDecimal pay = new BigDecimal(oneSecondHrk);
        charged = timeInSec.multiply(pay);
        compare = tempBalance.compareTo(charged);

        if (compare == 1) {

            text.appendText("Account " + acc.getKey() + " is calling " + newLine);
            //text.setText("Call will last for " + timeInSec + " seconds");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                        System.out.println("Calling");
                    }
                }
            });
            t.start();

            // Schedule task to terminate thread in 1 minute
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    t.interrupt();
                    text.appendText("Call has ended " + newLine);
                    newBalance = tempBalance.subtract(charged);

                    if (acc.getCurrency() == 1) {
                        try {
                            text.appendText("Account is using currency model HRK " + newLine);
                            callCon = new CallController();
                            accCon = new AccountController();

                            try {
                                Call call = new Call();
                                createCall(call, acc, charged.setScale(2, RoundingMode.CEILING));
                                text.appendText("Trying to insert call in database " + newLine);
                                callCon.insertCall(call);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Call was charged " + charged.setScale(2, RoundingMode.CEILING) + " HR kuna's " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for call " + newLine);
                            }

                        } catch (Exception e) {
                            text.appendText("Unable to charge call, error " + newLine);
                            e.printStackTrace();
                            return;
                        }
                    }

                    if (acc.getCurrency() == 2) {
                        try {
                            text.appendText("Account is using currency model EURO " + newLine);
                            callCon = new CallController();
                            BigDecimal pay = new BigDecimal(oneSecondEuro);
                            charged = timeInSec.multiply(pay);
                            try {
                                Call call = new Call();
                                createCall(call, acc, charged.setScale(2, RoundingMode.CEILING));
                                text.appendText("Trying to insert call in database " + newLine);
                                callCon.insertCall(call);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Call was charged " + charged + " EUROS " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for call " + newLine);
                            }
                        } catch (Exception e) {
                            text.appendText("Unable to charge call, error " + newLine);
                            return;
                        }

                    }
                }
            }, timeToRun, TimeUnit.MILLISECONDS);

            // Start thread
        } else {
            text.appendText("insufficient funds, please recharge account");
            stopThread();
        }

    }

    public static void stopThread() {
        ChargingSystem.interrupted();
    }

    public void sms(Account acc, TextArea text) {

        tempBalance = acc.getInitbalance();

        BigDecimal k = new BigDecimal(1000);
        timeToRun = (long) 3000;
        timeInMillSec = new BigDecimal(timeToRun);
        timeInSec = timeInMillSec.divide(k);
        text.appendText("Sending of sms will be done in " + timeInSec + " seconds" + newLine);
        currentTime = System.currentTimeMillis();
        BigDecimal pay = new BigDecimal(oneSmsHrk);
        charged = timeInSec.multiply(pay);
        compare = tempBalance.compareTo(charged);

        if (compare == 1) {

            text.appendText("Account " + acc.getKey() + " is sending sms " + newLine);
            //text.setText("Call will last for " + timeInSec + " seconds");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                        System.out.println("Sending  of sms's is in progress ");
                    }
                }
            });
            t.start();

            // Schedule task to terminate thread in 1 minute
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    t.interrupt();
                    text.appendText("Sending of sms is finished " + newLine);
                    newBalance = tempBalance.subtract(charged);

                    if (acc.getCurrency() == 1) {
                        try {
                            text.appendText("Account is using currency model HRK " + newLine);
                            smsCon = new SmsController();

                            try {
                                Sms sms = new Sms();
                                createSms(sms, acc, charged.setScale(2, RoundingMode.CEILING));
                                text.appendText("Trying to insert sent sms(s) in database " + newLine);
                                smsCon.insertSms(sms);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Sms(s) was charged " + charged.setScale(2, RoundingMode.CEILING) + " HR kunas " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for sent sms " + newLine);
                            }

                        } catch (Exception e) {
                            text.appendText("Unable to charge sms, error " + newLine);
                            e.printStackTrace();
                            return;
                        }
                    }

                    if (acc.getCurrency() == 2) {
                        try {
                            text.appendText("Account is using currency model EURO" + newLine);
                            smsCon = new SmsController();
                            BigDecimal pay = new BigDecimal(oneSmsEuro);
                            charged = timeInSec.multiply(pay);
                            try {
                                Sms sms = new Sms();
                                createSms(sms, acc, charged);
                                text.appendText("Trying to insert sent sms(s) in database " + newLine);
                                smsCon.insertSms(sms);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Sms(s) was charged " + charged.setScale(2, RoundingMode.CEILING) + "EUROS " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for sent sms " + newLine);
                            }
                        } catch (Exception e) {
                            text.appendText("Unable to charge sms, error " + newLine);
                            return;
                        }

                    }
                }
            }, timeToRun, TimeUnit.MILLISECONDS);

            // Start thread
        } else {
            text.appendText("insufficient funds, please recharge account");
            stopThread();

        }
    }

    public void mms(Account acc, TextArea text) {

        tempBalance = acc.getInitbalance();

        BigDecimal k = new BigDecimal(1000);
        timeToRun = (long) 2000;
        timeInMillSec = new BigDecimal(timeToRun);
        timeInSec = timeInMillSec.divide(k);
        text.appendText("Sending of Mms will be done in " + timeInSec + " seconds" + newLine);
        currentTime = System.currentTimeMillis();
        BigDecimal pay = new BigDecimal(oneMmsHrk);
        charged = timeInSec.multiply(pay);
        compare = tempBalance.compareTo(charged);

        if (compare == 1) {

            text.appendText("Account " + acc.getKey() + " is sending Mms " + newLine);
            //text.setText("Call will last for " + timeInSec + " seconds");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                        System.out.println("Sending Mms ");
                    }
                }
            });
            t.start();

            // Schedule task to terminate thread in 1 minute
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    t.interrupt();
                    text.appendText("Sending of Mms is finished " + newLine);
                    newBalance = tempBalance.subtract(charged);
                    if (acc.getCurrency() == 1) {
                        try {
                            text.appendText("Account is using currency model HRK " + newLine);
                            mmsCon = new MmsController();

                            try {
                                Mms mms = new Mms();
                                createMms(mms, acc, charged.setScale(2, RoundingMode.CEILING));
                                text.appendText("Trying to insert sent Mms(s) in database " + newLine);
                                mmsCon.insertMms(mms);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Mms(s) was charged " + charged.setScale(2, RoundingMode.CEILING) + " HR kunas " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for sent Mms " + newLine);
                            }

                        } catch (Exception e) {
                            text.appendText("Unable to charge Mms, error " + newLine);
                            e.printStackTrace();
                            return;
                        }
                    }

                    if (acc.getCurrency() == 2) {
                        try {
                            text.appendText("Account is using currency model EURO" + newLine);
                            mmsCon = new MmsController();
                            BigDecimal pay = new BigDecimal(oneMmsEuro);
                            charged = timeInSec.multiply(pay);
                            try {
                                Mms mms = new Mms();
                                createMms(mms, acc, charged.setScale(2, RoundingMode.CEILING));
                                text.appendText("Trying to insert sent Mms(s) in database " + newLine);
                                mmsCon.insertMms(mms);
                                updateAccount(acc, newBalance.setScale(2, RoundingMode.CEILING));
                                accCon.updateAccount(acc);
                                text.appendText("Mms(s) was charged " + charged.setScale(2, RoundingMode.CEILING) + "Euros " + newLine);
                                text.appendText("Start new simulation if you want");
                            } catch (Exception e) {
                                e.printStackTrace();
                                text.appendText("Unable to insert data for sent Mms " + newLine);
                            }
                        } catch (Exception e) {
                            text.appendText("Unable to charge Mms, error " + newLine);
                            return;
                        }

                    }
                }
            }, timeToRun, TimeUnit.MICROSECONDS);

            // Start thread
        } else {
            text.appendText("insufficient funds, please recharge account");
            stopThread();

        }

    }

    public void createCall(Call call, Account acc, BigDecimal charged) {
        call.setAccId(acc.getId());
        call.setCharged(charged);
        call.setDuration(timeInMillSec);

    }

    public static void createSms(Sms sms, Account acc, BigDecimal charged) {
        sms.setAccId(acc.getId());
        sms.setCharged(charged);
        sms.setAmount(numberOfSms);

    }

    public static void createMms(Mms mms, Account acc, BigDecimal charged) {
        mms.setAccId(acc.getId());
        mms.setCharged(charged);
        mms.setAmount(numberOfMms);

    }

    public static void updateAccount(Account acc, BigDecimal newbalance) {
        acc.setInitbalance(newbalance);

    }

    public static long GenerateRandomCallTime() {
        long time = (long) (Math.random() * 10000 + 1);
        //10000 = 10 sec
        return time;
    }

    public static int GenerateRandomSmsSent() {
        int smsSent = (int) (Math.random() * 2 + 1); // max 3 sms can be sent
        //60000 = 1 min
        return smsSent;
    }

    public static int GenerateRandomMmsSent() {
        int mmsSent = (int) (Math.random() * 2 + 1); // max 3 mms can be sent
        //60000 = 1 min
        return mmsSent;
    }

    public static int randomId(ArrayList<Integer> listOfAccounts) {
        Random rand;
        rand = new Random();
        Integer randomInt = listOfAccounts.get(rand.nextInt(listOfAccounts.size()));
        return randomInt;

    }

    public void randomAccountAction(Account acc, Account acc2, TextArea text, TextArea textChatUsr1, TextArea textChatUsr2, Pane user1, Pane user2, Button stopSms) {
        Random rand = null;
        rand = new Random();
        int[] randomItems = new int[]{1, 2, 3};
        choice = rand.nextInt(randomItems.length);
        //choice = 1;

        System.out.println("Action selected " + choice);

        if (choice == 0) {
            text.appendText("Preparing to call " + newLine);
            call(acc, text);

        }

        if (choice == 1) {
            msgTrn1 = new MessageTransmiter1();
            msgTrn2 = new MessageTransmiter2();
            msgLis1 = new MessageListener1(textChatUsr1);
            msgLis2 = new MessageListener2(textChatUsr2);
            

            user1.setVisible(true);
            user2.setVisible(true);
            stopSms.setVisible(true);
            text.appendText("You can now start sending msg+s btw users " + newLine);

        }

        if (choice == 2) {
            text.appendText("Preparing to send mms" + newLine);
            mms(acc, text);

        }

    }

    public static long chargeCall(Long charged) {

        return charged;
    }

    public static long chargeSms(Long charged) {

        return charged;
    }

    public static long chargeMms(Long charged) {

        return charged;
    }

    public Integer getAccRan() {
        return accRan;
    }

    public void setAccRan(Integer accRan) {
        this.accRan = accRan;
    }

    public void sendSmsFromUser1(String sms, TextArea text) {
        text.appendText("Deploying text to chat2" + sms);
        msgTrn1.getInstance();
        msgTrn1.sendMsg(sms);
        sms(acc, text);
    }
    /* public void setChatArea1(TextArea chat1){
        msgLis1.startListener(chat1);
    }
    public void setChatArea2(TextArea chat2){
        msgLis2.startListener(chat2);
    }
*/
   
    public void sendSmsFromUser2(String sms, TextArea text) {
        text.appendText("Deploying text to chat1 " + sms);
        msgTrn2.getInstance();
        msgTrn2.sendMsg(sms);
        sms(acc2, text);
    }

    public void stopMsgService1() {
        msgLis1.stoptListener();
        msgTrn1.stoptTransmiter();
    }

    public void stopMsgService2() {
        msgLis2.stoptListener();
        msgTrn2.stoptTransmiter();
    }

}
