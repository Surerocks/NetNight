import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

import hackasaurusrex.netnight.MainActivity;

/**
 * Created by Craig on 02/04/2016.
 */
public class SendEmail {
    public static final String DEFAULT_SUBJECT = "Subject Line";
    public static final String DEFAULT_TEXT = "Body of email";

    private String subject;
    private String text;
    private File file;
    private Activity activity;

    public SendEmail(String subject, String text, String filename){
        this.subject = subject;
        this.text = text;

        //TODO Validate file
        file = new File(filename);
    }

    public SendEmail(String filename){
        this(DEFAULT_SUBJECT, DEFAULT_TEXT, filename);
    }

    public void setText(String text){
        this.text = text;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    /**
     * Sends an email to an email client with the default subject, message body, and file attachment
     *  Sends to the specified recipient email
     * @param recipientEmail The email address of the recipient
     */
    public void sendEmail(String recipientEmail){
        // Set up the email to send
        Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_EMAIL, recipientEmail);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, text);

        Uri u = Uri.fromFile(file);
        i.putExtra(Intent.EXTRA_STREAM, u);

        // Get user to select which mail client to use
        try{
            activity.startActivity(Intent.createChooser(i,"Choose an email clien to send from:"));
        } catch (android.content.ActivityNotFoundException e){
            // TODO notify user that no client installed
            Toast.makeText(activity, "No email clients found", Toast.LENGTH_LONG).show();
        }

    }
}
