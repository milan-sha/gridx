package com.example.electricox.CHATBOT;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;


import com.example.electricox.R;

import java.util.List;

public class chatbot extends AppCompatActivity {

    private QADao qaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        qaDao = new QADao(getApplicationContext());

        qaDao.insertQA("Hello", "Hi there!");
        qaDao.insertQA("Bye,Bie", "See you later! If you ever need advice or just want to chat, I'll be here. Stay fit and happy, goodbye!");
        qaDao.insertQA("How are you?", "I'm fine, thank you!");
        qaDao.insertQA("Hi", "Hi there!");
        qaDao.insertQA("What is Your Name ", "I am a simple chatbot!!");
        qaDao.insertQA("Where can I find the nearest EV charging station?",
                "You can find the nearest charging station using our app's location feature. Enable GPS or enter your location manually.");
        qaDao.insertQA("How do I book a charging slot?",
                "Go to the 'Book a Slot' section in the app, select your preferred station, choose a time slot, and confirm your booking.");
        qaDao.insertQA("What types of chargers are available?",
                "Our stations support Level 1, Level 2, and DC fast chargers. Check station details in the app for compatibility.");
        qaDao.insertQA("How much does it cost to charge my EV?",
                "Charging costs vary by station and charger type. Check the pricing details in the app under the station's information.");
        qaDao.insertQA("How long does it take to charge my EV?",
                "Charging time depends on your EV and charger type. Level 1 takes several hours, Level 2 is faster, and DC fast chargers can charge up to 80% in 30-45 minutes.");
        qaDao.insertQA("Can I pay online for charging?",
                "Yes, you can pay via credit/debit cards, UPI, or digital wallets through our app.");
        qaDao.insertQA("What happens if I miss my booked charging slot?",
                "If you miss your slot, you may need to rebook. Cancellation policies vary by station, so check the app for details.");
        qaDao.insertQA("Do I need an account to use the charging stations?",
                "Yes, creating an account helps track your bookings, payments, and charging history.");
        qaDao.insertQA("Are the charging stations available 24/7?",
                "Availability depends on the station. Some are open 24/7, while others have specific operating hours.");
        qaDao.insertQA("How do I report a faulty charging station?",
                "Use the 'Report Issue' button in the app or contact our support team for assistance.");
        qaDao.insertQA("What should I do if a charging station is not working?",
                "If a station is not working, try restarting the session. If the issue persists, report it through the app's 'Report Issue' feature.");
        qaDao.insertQA("What type of connector does my EV need?",
                "The connector type depends on your EV model. Common types include Type 2, CCS, and CHAdeMO. Check your vehicle manual or app for details.");
        qaDao.insertQA("What happens if there is a power outage while charging?",
                "If there's a power outage, charging will stop automatically. You may need to restart the session once power is restored.");
        qaDao.insertQA("What is the difference between Level 1, Level 2, and DC fast charging?",
                "Level 1 uses a standard outlet and is slow. Level 2 is faster and ideal for home and public charging. DC fast charging is the quickest option, often used for long trips.");
        qaDao.insertQA("What is regenerative braking and how does it help EVs?",
                "Regenerative braking converts braking energy into electricity, improving efficiency and extending driving range.");
        qaDao.insertQA("How do I find charging stations along my route?",
                "Use the app’s 'Route Planner' feature to locate charging stations along your journey.");
        qaDao.insertQA("How can I maximize my EV’s battery life?",
                "Avoid frequent fast charging, keep your battery between 20-80%, and avoid extreme temperatures.");
        qaDao.insertQA("How do I know when my EV is fully charged?",
                "Most EVs notify you via the dashboard or mobile app when fully charged. You can also check the charging station screen.");
        qaDao.insertQA("How can I use my EV for long trips?",
                "Plan your route with charging stations in mind, maintain moderate speed, and use regenerative braking effectively.");
        qaDao.insertQA("How do I check my charging history?",
                "Go to the 'Charging History' section in the app to view your past sessions and payments.");
        qaDao.insertQA("Use the app to locate nearby charging stations.",
                "Simply open the app, enable location services, and it will display nearby stations.");
        qaDao.insertQA("Use fast charging only when necessary.",
                "Frequent fast charging can degrade battery health over time. Use Level 2 charging for regular use.");
        qaDao.insertQA("What payment methods are accepted for EV charging?",
                "You can pay via credit/debit cards, UPI, digital wallets, or the app’s prepaid balance.");
        qaDao.insertQA("How do I use a public charging station?",
                "Plug in your EV, authenticate via the app, start the session, and unplug when done.");
        qaDao.insertQA("How can I report a charging station issue?",
                "Use the 'Report Issue' button in the app or contact customer support.");
        qaDao.insertQA("Is there an idle fee for overstaying at a charging station?",
                "Yes, some stations charge an idle fee if you leave your vehicle plugged in after it's fully charged. Check station policies in the app.");
        qaDao.insertQA("Can I reserve a charging station in advance?",
                "Yes, you can book a charging slot in advance using the 'Book a Slot' feature in our app.");
        qaDao.insertQA("How do I check the availability of a charging station?",
                "You can check real-time availability of charging stations in the app under the 'Charging Stations' section.");
        qaDao.insertQA("Do charging stations support all EV models?",
                "Most stations support standard connectors like CCS, CHAdeMO, and Type 2. Verify your EV’s compatibility in the app.");
        qaDao.insertQA("What should I do if my vehicle is not charging?",
                "Ensure the charger is properly connected. If the issue persists, restart the session or report it via the app.");
        qaDao.insertQA("Can I charge my electric bike or scooter at these stations?",
                "Some stations have dedicated chargers for electric bikes and scooters. Check station details in the app.");
        qaDao.insertQA("How do I cancel a charging reservation?",
                "Go to your bookings in the app and cancel your reservation. Cancellation policies may vary by station.");
        qaDao.insertQA("Is customer support available 24/7?",
                "Yes, our support team is available 24/7. You can reach us through the 'Support' section in the app.");
        qaDao.insertQA("What safety precautions should I take while charging?",
                "Avoid using damaged cables, do not leave your vehicle unattended for long, and ensure the charging plug is securely connected.");
        qaDao.insertQA("Can I get a receipt for my charging session?",
                "Yes, a receipt is available in the app under 'Transaction History' after completing your session.");

        EditText inputEditText = findViewById(R.id.inputEditText);
        findViewById(R.id.sendButton).setOnClickListener(v -> {
            String userInput = inputEditText.getText().toString().trim().toLowerCase();
            String chatbotResponse = getResponse(userInput);

            // Add user message
            addUserMessage(userInput);

            // Add chatbot message
            addChatbotMessage(chatbotResponse);

            // Clear the input field
            inputEditText.setText("");
        });
    }

    private void addUserMessage(String message) {
        LinearLayout chatContainer = findViewById(R.id.chatContainer);
        CardView userCard = createUserCard(message);
        chatContainer.addView(userCard);
    }

    private void addChatbotMessage(String message) {
        LinearLayout chatContainer = findViewById(R.id.chatContainer);
        CardView chatbotCard = createChatbotCard(message);
        chatContainer.addView(chatbotCard);
    }

    private CardView createUserCard(String message) {
        CardView cardView = new CardView(this);
        cardView.setLayoutParams(createUserCardLayoutParams()); // Switched to user layout params
        cardView.setCardBackgroundColor(getResources().getColor(R.color.myPrimary));
        cardView.setRadius(16); // Set radius for rounded corners
        cardView.setCardElevation(8); // Set elevation for a shadow effect

        TextView labelTextView = createLabelTextView("User:");
        TextView textView = createMessageTextView(message);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(labelTextView);
        linearLayout.addView(textView);

        cardView.addView(linearLayout);

        return cardView;
    }

    private CardView createChatbotCard(String message) {
        CardView cardView = new CardView(this);
        cardView.setLayoutParams(createChatbotCardLayoutParams()); // Switched to chatbot layout params
        cardView.setCardBackgroundColor(getResources().getColor(R.color.black));
        cardView.setRadius(16); // Set radius for rounded corners
        cardView.setCardElevation(8); // Set elevation for a shadow effect

        TextView labelTextView = createLabelTextView("Bot:");
        TextView textView = createMessageTextView(message);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(labelTextView);
        linearLayout.addView(textView);

        cardView.addView(linearLayout);

        return cardView;
    }

    private TextView createLabelTextView(String label) {
        TextView textView = new TextView(this);
        textView.setText(label);
        textView.setTextColor(getResources().getColor(android.R.color.darker_gray));
        textView.setTextSize(18); // Set the font size
        textView.setPadding(16, 0, 16, 4); // Adjust padding as needed
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.quicksand_medium)); // Set the font family
        return textView;
    }

    private TextView createMessageTextView(String message) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(20); // Set the font size
        textView.setPadding(16, 4, 16, 16); // Adjust padding as needed
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.quicksand_medium)); // Set the font family
        return textView;
    }


    private LinearLayout.LayoutParams createUserCardLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30, 8, 0, 8);
        return layoutParams;
    }

    private LinearLayout.LayoutParams createChatbotCardLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 30, 8);
        return layoutParams;
    }

    private String getResponse(String userQuery) {
        List<QAModel> qaList = qaDao.getAllQA();
        for (QAModel qaModel : qaList) {
            String question = qaModel.getQuestion().toLowerCase();
            if (userQuery.contains(question) || question.contains(userQuery)) {
                return qaModel.getAnswer();
            }
        }

        return "I'm a simple chatbot. You can ask me questions like:\n" +
                "- Where can I find the nearest EV charging station?\n" +
                "- How do I book a charging slot?\n" +
                "- What types of chargers are available?\n" +
                "- How much does it cost to charge my EV?\n" +
                "- How long does it take to charge my EV?\n" +
                "- Can I pay online for charging?\n" +
                "- How do I report a faulty charging station?\n" +
                "Feel free to ask anything related to EV charging!";
    }
}
