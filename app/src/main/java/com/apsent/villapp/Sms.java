package com.apsent.villapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class Sms
{
    private String txt;
    private String phone;
    private static final int phoneLen = 11;

    public static final String SERVER_SEPAHAN_GOSTAR="sepahan_gostar";
    public static final String SERVER_MEHR_AFRAZ="mehr_afraz";
    public static final String SERVER_PAYAN_RESAN="payam_resan";

    private String ServerName;


    private static final String SmsService_mehrAfraz="http://www.mehrafraz.com/sms/default.aspx";
    private static final String Sender_mehrAfraz="30008500099000";

    private static final String SmsService_payamResan="https://www.payam-resan.com/APISend.aspx";
    private static final String Sender2="50002030007213";
    private static final String Sender1="50002060499999";


    private static final String SmsService_SepahanGostar="http://login.sepahangostar.com/sendSmsViaURL.aspx";
    private static final String Sender3="50001299503979";
    /**
     * Send sms .
     *
     * @param txt - some text .
     * @param phone - mobile phone number .
     */
    public Sms(String ServiceName,String txt, String phone) {
        this.ServerName=ServiceName;
        this.txt = txt;
        this.phone = phone;
    }

    boolean Send()
    {
        if (IsValidPhone(this.phone))
        {
            String[] param = {this.phone,this.txt};
            try{
                    if(this.ServerName.contains(SERVER_SEPAHAN_GOSTAR))
                    {
                        Long result = new SendMsgTask_SepahanGostar().execute(param).get();//Sepahan-gostar
                        if (result > 1)//SendSuccess
                            return true;
                        else
                            return false;
                    }
                    else if(this.ServerName.contains(SERVER_MEHR_AFRAZ))
                    {
                        Long result= new SendMsgTask_MehrAfraz().execute(param).get();//Sepahan-gostar
                        if (result > 1)//SendSuccess
                            return true;
                        else
                            return false;
                    }
                    else if(this.ServerName.contains(SERVER_PAYAN_RESAN))
                    {
                        Integer result= new SendMsgTask().execute(param).get();//payam-resan
                        if (result == 1)//SendSuccess
                            return true;
                        else
                            return false;
                    }

            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }
    public  static boolean IsValidPhone(String phone)
    {
        if (phone.contains("09") && phone.length()==phoneLen)
        {
            return true;
        }
        return false;
    }
    public class SendMsgTask extends AsyncTask<String,Object,Integer>
    {
        @Override
        protected void onPostExecute(Integer integer) {
        }
        @Override
        protected Integer doInBackground(String... strings) {
            try {

                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(getSmsUrl_from_payamResan(strings[0],strings[1]))
                                .build()
                )
                        .execute()
                        .body()
                        .string();
                return Integer.valueOf(strApi);

            }
            catch (Exception e)
            {
                return 0;
            }
        }
    }
    public class SendMsgTask_SepahanGostar extends AsyncTask<String,Object,Long>
    {
        @Override
        protected void onPostExecute(Long integer) {
        }
        @Override
        protected Long doInBackground(String... strings) {
            try {

                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(getSmsUrl_from_sepahaGostar(strings[0],strings[1]))
                                .build()
                )
                        .execute()
                        .body()
                        .string();

                return Long.valueOf(strApi.substring(0,strApi.indexOf("\r\n")));

            }
            catch (Exception e)
            {
                return Long.valueOf(0);
            }
        }
    }
    public class SendMsgTask_MehrAfraz extends AsyncTask<String,Object,Long>
    {
        @Override
        protected void onPostExecute(Long integer) {
            Log.d("","Long="+String.valueOf(integer));
        }
        @Override
        protected Long doInBackground(String... strings) {
            try {

                String strApi=new OkHttpClient().newCall(
                        new Request.Builder()
                                .url(getSmsUrl_from_mehrAfraz(strings[0],strings[1]))
                                .build()
                )
                        .execute()
                        .body()
                        .string();

                String start="id=\"Label1\">";
                String stop="</span>&nbsp;";
                return Long.valueOf(strApi.substring(strApi.lastIndexOf(start)+start.length(),strApi.lastIndexOf(stop)));

            }
            catch (Exception e)
            {
                return Long.valueOf(0);
            }
        }
    }

    private String getSmsUrl_from_mehrAfraz(String phone , String text)
    {
        return SmsService_mehrAfraz+"?cbody="+text+"&cmobileno="+phone+"&cUsername=tsco&cpassword=Aa123456&cDomainName=tsco&cEncoding=1&cfromnumber="+Sender_mehrAfraz;
    }
    private String getSmsUrl_from_payamResan(String phone , String text)
    {
        return SmsService_payamResan+"?Username=09195835135&Password=a09367854752A&From="+Sender1+"&To="+phone+"&Text="+text+"";
    }
    private String getSmsUrl_from_sepahaGostar(String phone , String text)
    {
        return SmsService_SepahanGostar+"?userName=te" +
                "co&password=123456&domainName=sepahansms&smsText="+text+"&reciverNumber="+phone+"&senderNumber="+Sender3+"";
    }
}

