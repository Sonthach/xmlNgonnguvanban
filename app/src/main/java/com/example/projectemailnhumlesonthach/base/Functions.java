package com.example.projectemailnhumlesonthach.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Vibrator;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {
    // Mang cac ky tu goc co dau
    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự'};

    // Mang cac ky tu thay the khong dau
    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u'};

    private static String[] TEXT_CASE = {"A", "Ă", "Â", "B", "C", "D", "Đ", "E", "Ê", "G", "H", "I", "K", "L", "M", "N", "O", "Ô", "Ơ", "P", "Q", "R", "S", "T", "U", "Ư", "V", "X", "Y"};

    private static int[] NUM = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static Functions share = new Functions();

    public void hideNavBar(Activity activity) {
        // Hiding navigation bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /* Get current width of screen */
    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /* Get current Height of screen */
    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public String formatNumberMoney(double s) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        try {
            format.format(s);
            return format.format(s).replaceAll("\\.", ","); // Some devices return like 1.000 => 1,000
        } catch (Exception e) {
            return "";
        }
    }

    public String formatMoneyVND(double s) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        try {
            format.format(s);
            return (format.format(s) + " VND").replaceAll("\\.", ","); // Some devices return like 1.000 => 1,000
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeCreate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        java.util.Date date = new java.util.Date();
        String dateString = "";
        try {
            dateString = df.format(date);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String formatMoneyUSD(double s) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        try {
            format.format(s);
            return ("$ " + format.format(s)).replaceAll("\\.", ","); // Some devices return like 1.000 => 1,000
        } catch (Exception e) {
            return "";
        }
    }

    public String formatLongToString(long l) {
        DateFormat df = new SimpleDateFormat("dd/MM - HH:mm");
        String dateString = "";
        try {
            Date d = new Date(l);
            dateString = df.format(d);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String formatLongToDayoff(long l) {
        DateFormat df = new SimpleDateFormat("dd/MM");
        String dateString = "";
        try {
            Date d = new Date(l);
            dateString = df.format(d);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String formatLongToDay(long l) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = "";
        try {
            Date d = new Date(l);
            dateString = df.format(d);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public long formatStringToLong(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date d = sdf.parse(date);
            long l = d.getTime();
            return l;
        } catch (Exception e) {
            return 0;
        }
    }

    public long formatStringToLongDateTime(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            java.util.Date d = sdf.parse(date);
            long l = d.getTime();
            return l;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getToDay(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        String dateString = "";
        try {
            dateString = df.format(date);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String getToDayDateTime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        String dateString = "";
        try {
            dateString = df.format(date);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public String getLastTimeMessage(){
        DateFormat df = new SimpleDateFormat("dd/MM/ - HH:mm");
        java.util.Date date = new java.util.Date();
        String dateString = "";
        try {
            dateString = df.format(date);
            return  dateString;
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPasswordCorrectRegister(String pass) {
        return pass.length() >= 4;
    }

    public boolean isPasswordCorrect(String pass) {
        return pass.length() >= 8 && isHaveNumber(pass) && isHaveCase(pass);
    }

    private boolean isHaveNumber(String pass) {
        for (char c : pass.toCharArray()) {
            for (int c1 : NUM) {
                if ((c1 + "").equals(c + "")) return true;
            }
        }
        return false;
    }

    private boolean isHaveCase(String pass) {
        for (char c : pass.toCharArray()) {
            for (String c1 : TEXT_CASE) {
                if ((c1).equals(c + "")) return true;
            }
        }
        return false;
    }

    public String formatMoneyD(double s) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        try {
            format.format(s);
            return (format.format(s) + " đ").replaceAll("\\.", ","); // Some devices return like 1.000 => 1,000
        } catch (Exception e) {
            return "";
        }
    }

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    /**
     * Bo dau 1 chuoi
     *
     * @param s String
     * @return String after removed
     */
    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    // This method help remove html tag in a string
    public Spanned stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    public boolean isEdttEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    // Check Email validate
    public boolean isEmailValidate(String email) {
        if (TextUtils.isEmpty(email)) return false;
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void Toast(Context context, String thongbao) {
        Toast.makeText(context,thongbao,Toast.LENGTH_SHORT).show();
    }

    public boolean isPhoneValidate(String phone) {
        return phone.length() > 0;
    }

    public String encodeBase64(String s) {
        String res = "";
        try {
            byte[] data = s.getBytes("UTF-8");
            res = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res.trim();
    }

    public float getFileSizeInMB(File fileName) {
        float ret = getFileSizeInBytes(fileName);
        ret = ret / (float) (1024 * 1024);
        return ret;
    }

    private static long getFileSizeInBytes(File f) {
        long ret = 0;
        if (f.isFile()) {
            return f.length();
        } else if (f.isDirectory()) {
            File[] contents = f.listFiles();
            for (File content : contents) {
                if (content.isFile()) {
                    ret += content.length();
                } else if (content.isDirectory())
                    ret += getFileSizeInBytes(content);
            }
        }
        return ret;
    }

    public String decodeBase64(String s) {
        String res = "";
        try {
            byte[] data = Base64.decode(s, Base64.DEFAULT);
            res = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res.trim();
    }

    public void startVibrator(Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(500); // for 500 ms
        }
    }


    public int getQuality(File file) {
        float fileSize = getFileSizeInMB(file);

        if (fileSize < 1.3) return 100;

        float buff = fileSize / 1.4f;

        return (int) (100 / buff);
    }

    public String getDurationString(int seconds) {
        if (seconds < 0 || seconds > 2000000)//there is an codec problem and duration is not set correctly,so display meaningfull string
            seconds = 0;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        if (hours == 0)
            return twoDigitString(minutes) + ":" + twoDigitString(seconds);
        else
            return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public File persistImage(Bitmap bitmap, File oldFile, int quality) {
        File imageFile = new File(oldFile + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("TAG", "Error writing bitmap", e);
        }
        return imageFile;
    }
}

