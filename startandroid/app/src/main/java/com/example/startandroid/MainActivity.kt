package com.example.startandroid

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset
import android.content.Context
import android.widget.Toast
import java.io.*
import java.nio.charset.StandardCharsets
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Environment;
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log;

class MainActivity : AppCompatActivity() {

    private var mHelloTextView: TextView? = null
    private var mInternetTextView: TextView? = null
    private var mNameEditText: EditText? = null
    private var worker: MyThread? = null
    private var mCPU0WorkTest: TextView? = null
    private var listFreqForCPU0First: MutableMap<String?, String?>? = null
    private var listFreqForCPU0Last: MutableMap<String?, String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHelloTextView = findViewById(R.id.textView) as TextView
        mNameEditText = findViewById(R.id.editText)


    }

    fun onClick(view: View) {
        if (worker == null) {
            worker = MyThread()
            worker?.start()
            mHelloTextView!!.setText("work thread");
        } else {
            worker?.Cancel()
            worker?.join()
            worker = null
            mHelloTextView!!.setText("idle")
        }
        /*if (mNameEditText!!.getText().length == 0){

        } else {
            mHelloTextView!!.setText("Привет, " + mNameEditText!!.getText())
        }*/

    }

    fun onClickThread() {
        if (worker == null) {
            worker = MyThread()
            worker?.start()
            mHelloTextView!!.setText("work thread");
        } else {
            worker?.Cancel()
            worker?.join()
            worker = null
            mHelloTextView!!.setText("idle")
        }
    }

    fun parserTimeInState(file: File) : MutableMap<String?, String?>? {
        /*val input: InputStream = file.inputStream()
        val baos = ByteArrayOutputStream()
        input.use { it.copyTo(baos) }
        val inputAsString = baos.toString()*/
        var listStatistic = mutableMapOf<String?, String?>()
        //BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
        val bufferedRead = BufferedReader(FileReader(file))
        var freq: String? = ""
        var value: String? = ""
        var i = 0
        var sum = ""
        for (line in bufferedRead.readLines()) {
            val matchedResults = Regex(pattern = """\d+""").findAll(input = line)
            for (matchedText in matchedResults) {
                if (i == 0) {
                    freq = matchedText.value
                    i++
                } else if (i == 1) {
                    value = matchedText.value
                    i = 0
                } else return listStatistic
            }
            /*for (word in line.split(Regex("\\s+"))){
                if (i == 0) {
                    freq = word
                    i++
                } else {
                    value = word.toInt()
                    i = 0
                }
            }*/
            listStatistic?.put(freq, value)
            sum += value + "\n"
        }
        bufferedRead.close()
        return listStatistic
    }

    fun internetButtonOnClick(view: View) {
        /*val randomId = Math.abs(Random.nextInt()) % 10 + 1
        NetworkService.getInstance()
            .jsonApi
            .getPostWithID(randomId)
            .enqueue(object : retrofit2.Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    val post = response.body()!!
                    mInternetTextView = findViewById(R.id.textView2) as TextView

                    mInternetTextView?.text = ""
                    mInternetTextView?.append("${post.getId()}\n")
                    mInternetTextView?.append("${post.getUserId()}\n")
                    mInternetTextView?.append(post.getTitle() + "\n")
                    mInternetTextView?.append(post.getBody() + "\n")
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {

//                    textView.append("Error occurred while getting request!")
                    t.printStackTrace()
                }
            })*/
        //var root: File = Environment.getExternalStorageDirectory()
       // var root: File = Environment.getRootDirectory()

        /*val root = File("./../../../../sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state")
        mInternetTextView = findViewById(R.id.textView2) as TextView
        val inputStream: InputStream = root.inputStream()
        val cpu0string: String?
        for (line in root.readLines()) {
            mInternetTextView?.append(line)
        }*/

        /*val inputString = inputStream.bufferedReader().use { it.readText() }
        if (mInternetTextView?.getText() == "irishka\n"){
            mInternetTextView?.append(inputString)
        } else {
            mInternetTextView?.setText("irishka\n")
            mInternetTextView?.append(inputString)
        }*/

        /*root.listFiles().forEach {
            mInternetTextView?.append("${it.name}\n")
        }*/
        /*val cpu0TimeInState = File("./../../../../sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state")
        mInternetTextView = findViewById(R.id.textView2) as TextView
        val listFreqForCPU0 = parserTimeInState(cpu0TimeInState)
        var result = ""
        for (value in listFreqForCPU0!!.iterator()){
            result = value.key + " " + value.value
            mInternetTextView?.setText(result)
        }*/
        //mInternetTextView?.setText(listFreqForCPU0)
        //if (mHelloTextView?.getText() == "idle") {

        //}
        val cpu0TimeInState = File("./../../../../sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state")
        var freqFreq: String?
        var freqValue: String?
        var freqLine = ""
        var diff: Int
        if (worker == null) {
            mInternetTextView = findViewById(R.id.textView2) as TextView
            listFreqForCPU0First = parserTimeInState(cpu0TimeInState)
            worker = MyThread()
            worker?.start()
            mHelloTextView!!.setText("work thread");
        } else {
            worker?.Cancel()
            worker?.join()
            worker = null
            mHelloTextView!!.setText("idle")
            mInternetTextView?.setText("")
            listFreqForCPU0Last = parserTimeInState(cpu0TimeInState)
            for (freq in listFreqForCPU0Last!!.iterator()){
                freqFreq = freq?.key
                diff = freq?.value!!.toInt() - listFreqForCPU0First?.get(freqFreq)!!.toInt()
                freqValue = diff.toString()
                freqLine = freqFreq + " " + freqValue + "\n"
                mInternetTextView?.append(freqLine)
            }
        }
    }
}
