package com.example.vov4ik.quotes;

import android.content.Context;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vov4ik on 7/5/2016.
 */
public class fileWriter implements Serializable {
    /**This is only for manual using.

        /**
         * Create a String from the Object using Base64 encoding
         * @param object - any Object that is Serializable
         * @return - Base64 encoded string.
         */
 /*       public static String objectToString(Serializable object) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                new ObjectOutputStream(out).writeObject(object);
                byte[] data = out.toByteArray();
                out.close();

                out = new ByteArrayOutputStream();
                Base64OutputStream b64 = new Base64OutputStream(out,0);
                b64.write(data);
                b64.close();
                out.close();

                return new String(out.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
*/
        /**
         * Creates a generic object that needs to be cast to its proper object
         * from a Base64 ecoded string.
         *
         /*        * @param encodedObject
         * @return
         */
   /*   public static Object stringToObject(String encodedObject) {
            try {
                return new ObjectInputStream(new Base64InputStream(
                        new ByteArrayInputStream(encodedObject.getBytes()), 0)).readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
*/

        public static void WriteSettings(Context context, List<Quotes> q, String filename){
            FileOutputStream fOut = null;
            OutputStreamWriter osw = null;
            File file = new File(filename);
            int i = 0;
            try(
//                FileWriter fw = new FileWriter(file, true);
//                BufferedWriter bw = new BufferedWriter(fw);
//                PrintWriter out = new PrintWriter(fw))
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(
                            new FileOutputStream(file), "UTF-8"
                    )))
            {
                for (Quotes quote: q) {

                    i++;
                    String ser = (("QUOTE " + quote.getQuote() + " AUTHOR " + quote.getAuthor() + " TAG " + Arrays.toString(quote.getTags())));
                    out.println(ser);
                    Log.d("Test", " " + i);
//                    if (i == 1000) {
//                        break;
//                    }
                }
                out.close();
//                PrintWriter printWriter = new PrintWriter(file);
//                printWriter.println(data);
//                printWriter.flush();
//                printWriter.close();
                //fOut = (new FileOutputStream (file));
                //fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
                //osw = new OutputStreamWriter(fOut);
                //osw.write(data);
                //osw.flush();
                //osw.close();
                //fOut.write(data.getBytes());
                //fOut.close();
                //Toast.makeText(context, "Settings saved",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                // Toast.makeText(context, "Settings not saved",Toast.LENGTH_SHORT).show();
            }

        }

        /**
         * Read data from file and put it into a string
         * @param context
         * @param filename - fully qualified string name
         * @return
         */
     /*   public static String ReadSettings(Context context, String filename){
            StringBuffer dataBuffer = new StringBuffer();
            try{
                // open the file for reading
                InputStream instream = context.openFileInput(filename);
                // if file the available for reading
                if (instream != null) {
                    // prepare the file for reading
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);

                    String newLine;
                    // read every line of the file into the line-variable, on line at the time
                    while (( newLine = buffreader.readLine()) != null) {
                        // do something with the settings from the file
                        dataBuffer.append(newLine);
                    }
                    // close the file again
                    instream.close();
                }

            } catch (java.io.FileNotFoundException f) {
                // do something if the myfilename.txt does not exits
                Log.e(TAG, "FileNot Found in ReadSettings filename = " + filename);
                try {
                    context.openFileOutput(filename, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Log.e(TAG, "IO Error in ReadSettings filename = " + filename);
            }

            return dataBuffer.toString();
        }

    public  void writeToFile(List<Quotes> q, String path, Context mContext){



            //if (ser != null && !ser.equalsIgnoreCase("")) {
                fileWriter.WriteSettings(mContext, q, path);
            //} else {
//                fileWriter.WriteSettings(mContext, "", "myobject.dat");


    }*/
}
