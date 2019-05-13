package com.example.myapplication;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;


public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionS";

    private static final String appName = "BLuetoothApp";
    //UUID = an address, used to identify information
    private static final UUID MY_UUID_INSECURE =
            //UUID.fromString("075efdf0-199f-43ac-b643-90cb838b2e49");
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;
    private static ConnectedThread mConnectedThread;

    //test this toast function
    private  void toastMessage(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

    public BluetoothConnectionService(Context context){
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }

    /**
     * Thread is going to be waiting for a connection, until a connection is accepted
     *     or until cancelled (it behaves like a server-side client)
     */
    private class AcceptThread extends Thread{
        //Local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            //create new listening server socket
            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,MY_UUID_INSECURE);

                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);


            } catch (IOException e){

            }
            mmServerSocket = tmp;
        }
        //run method runs needs no call, it runs automatically when an accept thread object is created
        public void run(){
            Log.d(TAG, "run: AcceptThread Running.");
            BluetoothSocket socket = null;
            try{
                /**
                 * This is a blocking call
                 * It will only run on a successful connection or an exception
                 */
                Log.d(TAG, "run: RFCOM server socket start...");
                socket = mmServerSocket.accept();
                Log.d(TAG, "run: RFCOM server socket accepted connection.");
                toastMessage("Connected ! ");
            } catch (IOException e){
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());

            }
            if(socket != null){
                connected(socket,mmDevice);
            }
            Log.i(TAG, "END mAcceptThread");

        }
        public void cancel(){
            Log.d(TAG,"cancel: Canceling AcceptThread");
            try{
                mmServerSocket.close();
            } catch (IOException e){
                Log.e(TAG,"cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage());
            }
        }
    }

    /**
     * This thread runs while making an outgoing connection with a device
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }
        public void run(){
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread ");
            /**
             * get a BluetoothSocket for a connection
             * with the given Bluetooth
             */
            try {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                + MY_UUID_INSECURE);
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
                toastMessage("couldn't connect ");
            }

            mmSocket = tmp;
            //Always cancel discovery because it will slow down connection
            mBluetoothAdapter.cancelDiscovery();

            /**
             * Make a connection to the BluetoothSocket
             * This is a blocking call and will only run when
             * connection is successful or throw an exception
             */
            try {
                mmSocket.connect();
                Log.d(TAG, "run: ConnectThread connected.");
                //if the code gets past this point it means the connection was established successfully
            } catch (IOException e) {
                //Close socket
                try {
                    mmSocket.close();
                    Log.d(TAG,"run: Closed Socket");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectedThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE);
            }
            connected(mmSocket, mmDevice);
        }
        public void cancel(){
            try{
                Log.d(TAG,"cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mmSocket in ConnectionThread failed. " + e.getMessage());
            }
        }

    }

    /**
     * Start the chat service. Start AcceptThreat to begin a
     * session in listening mode. Called by Activity onResume()
     */
    public synchronized void start(){
        Log.d(TAG,"start");
        //Cancel any thread attempting to make a connection
        if(mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if(mInsecureAcceptThread == null){
            mInsecureAcceptThread = new AcceptThread();
            //this .start() is native to the thread object, it will initialize it
            mInsecureAcceptThread.start();
        }
    }

    /**
     * Accept threat starts and waits for a connection
     * ConnectThread starts and attemps to make a connection with the
     * other devices AcceptThread
     */
    public void startClient(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startClient: Started. ");

        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth", "Please wait...", true);
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
        //this toast would prove connection and show which device its connected to .
        toastMessage("successfully connected to " + device  );
    }

    /**
     * Responsible for maintaining the BTConnection, Sending data and receiving data through input/output streams
     */
    private class ConnectedThread extends Thread{
       private final BluetoothSocket mmSocket;
       private final InputStream mmInStream;
       private final OutputStream mmOutStream;

       public ConnectedThread(BluetoothSocket socket){
           Log.d(TAG, "ConnectedThread: Starting.");
           mmSocket = socket;
           InputStream tmpIn = null;
           OutputStream tmOut = null;

           //dismiss progressdialog when connection is established
           mProgressDialog.dismiss();
           try {
               tmpIn = mmSocket.getInputStream();
               tmOut = mmSocket.getOutputStream();
           } catch (IOException e) {
               e.printStackTrace();
           }
           mmInStream = tmpIn;
           mmOutStream = tmOut;
       }

       public void run(){
           //buffer store for the stream
           byte[] buffer = new byte[1024];
           int bytes; //bytes returned from read
           //TODO: improve while(true)
           while (true){
               try {
                   bytes = mmInStream.read(buffer);
                   String incomingMessage = new String(buffer, 0, bytes);
                   Log.d(TAG,"InputStream: " + incomingMessage);
               } catch (IOException e) {
                   //if there is problem with the inputstream we want to break the connection
                   Log.e(TAG, "write: Error reading input stream. " + e.getMessage());
                   break;
               }
           }
       }
       //Call this method from the main activity to send data
        public void write(byte[] bytes){
           String text = new String(bytes, Charset.defaultCharset());
           Log.d(TAG, "write: Writing to outputstrean: " + text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to outputstream. " + e.getMessage());
            }
        }
       //Call this method from the main activity to shut down connection
        public void cancel(){
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice){
        Log.d(TAG,"connected: Starting.");

        //Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    /**
     * write to the connectedThread in unsynchronized manner
     * @param out bytes to write
     * @see ConnectedThread#write(byte[])
     */

    public static void write(byte[] out) {
        //Create temporary object
        ConnectedThread r;

        //Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write called.");
        mConnectedThread.write(out);
    }

}
