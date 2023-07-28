package com.alphabravo.gadoapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class SignupPage extends AppCompatActivity {

    private Button  create;
    MyDatabaseHelper myDatabase; // SQLite
    private EditText email, password, nickname;
    private TextView signin;
    private CheckBox check;
    private FirebaseAuth auth;
    private ImageView question;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    //press again to exit
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    //press again to exit

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.usercredentialpage);

        myDatabase = new MyDatabaseHelper(SignupPage.this);

        Paper.init(this);

        email = findViewById(R.id.emailText1);
        password = findViewById(R.id.passwordText1);
        check = (CheckBox) findViewById(R.id.signUpCheckBox);
        create = (Button) findViewById(R.id.createBtn);
        signin = (TextView) findViewById(R.id.signintextBtn);
        auth = FirebaseAuth.getInstance();
        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        create.setEnabled(false);
        ImageView imageViewShowpass = findViewById(R.id.hidepass);
        imageViewShowpass.setImageResource(R.drawable.hide);
        question = (ImageView) findViewById(R.id.questionButton);
        nickname = findViewById(R.id.nickname);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        imageViewShowpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowpass.setImageResource(R.drawable.hide);
                } else {
                    password.setTransformationMethod((HideReturnsTransformationMethod.getInstance()));
                    imageViewShowpass.setImageResource(R.drawable.view);
                }
            }
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton CompoundButton, boolean b) {
                if (b) {
                    materialAlertDialogBuilder.setTitle("Terms & Conditions");
                    materialAlertDialogBuilder.setMessage("Last updated June 27, 2023\n" +
                            "\n" +
                            "AGREEMENT TO OUR LEGAL TERMS\n" +
                            "We are QuadroZENIX (\"Company,\" \"we,\" \"us,\" \"our\"), a company registered in the Philippines at Anonas cor., Pureza Street, Sta. Mesa, Manila, Metro Manila 1016.\n" +
                            "We operate the mobile application GADO (ff— F) (the \"App\"), as well as any other related products and services that refer to or link to these legal terms (the \"Legal Terms\") (collectively, the \"Services\").\n" +
                            "The game-based android application the QuadroZENIX company developed to provide a functional application for the public that primarily focuses on budgeting. As this may imply, its entertainment and interest of external audience will bound through financial gamified management app. The application aims to aid its users with their budgeting problems and helps its user develop good practices when it comes to handling money. The purpose of this program is to help decrease poverty as much as possible and encourage its users to help themselves practice discipline. Being a self-help or a self-improvement application, it is game based for feasible engagement.\n" +
                            "You can contact us by phone at 0123-456-789, email at gezreelwazarcon@gmail.com, or by mail to Anonas cor., Pureza Street, Sta. Mesa, Manila, Metro Manila 1016, Philippines.\n" +
                            "These Legal Terms constitute a legally binding agreement made between you, whether personally or on behalf of an entity (\"you\"), and QuadroZENIX, concerning your access to and use of the Services. You agree that by accessing the Services, you have read, understood, and agreed to be bound by all of these Legal Terms. IF YOU DO NOT AGREE WITH ALL OF THESE LEGAL TERMS, THEN YOU ARE EXPRESSLY PROHIBITED FROM USING THE SERVICES AND YOU MUST DISCONTINUE USE IMMEDIATELY.\n" +
                            "We will provide you with prior notice of any scheduled changes to the Services you are using. The modified Legal Terms will become effective upon posting or notifying you by villarizaced@gmail.com, as stated in the email message. By continuing to use the Services after the effective date of any changes, you agree to be bound by the modified terms.\n" +
                            "All users who are minors in the jurisdiction in which they reside (generally under the age of 18) must have permission of, and be directly supervised by, their parent or guardian to use the Services. If you are a minor, you must have your parent or guardian read and agree to these Legal Terms prior to you using the Services.\n" +
                            "We recommend that you print a copy of these Legal Terms for your records.\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "TABLE OF CONTENTS\n" +
                            "1. OUR SERVICES\n" +
                            "2. INTELLECTUAL PROPERTY RIGHTS\n" +
                            "3. USER REPRESENTATIONS\n" +
                            "4. USER REGISTRATION\n" +
                            "5. PROHIBITED ACTIVITIES\n" +
                            "6. USER GENERATED CONTRIBUTIONS\n" +
                            "7. CONTRIBUTION LICENSE\n" +
                            "8. MOBILE APPLICATION LICENSE\n" +
                            "9. SOCIAL MEDIA\n" +
                            "10. ADVERTISERS\n" +
                            "11. SERVICES MANAGEMENT\n" +
                            "12. PRIVACY POLICY\n" +
                            "13. TERM AND TERMINATION\n" +
                            "14. MODIFICATIONS AND INTERRUPTIONS\n" +
                            "15. GOVERNING LAW\n" +
                            "16. DISPUTE RESOLUTION\n" +
                            "17. CORRECTIONS\n" +
                            "18. DISCLAIMER\n" +
                            "19. LIMITATIONS OF LIABILITY\n" +
                            "20. INDEMNIFICATION\n" +
                            "21. USER DATA\n" +
                            "22. ELECTRONIC COMMUNICATIONS, TRANSACTIONS, AND SIGNATURES\n" +
                            "23. MISCELLANEOUS\n" +
                            "24. CONTACT US\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "1. OUR SERVICES\n" +
                            "The information provided when using the Services is not intended for distribution to or use by any person or entity in any jurisdiction or country where such distribution or use would be contrary to law or regulation or which would subject us to any registration requirement within such jurisdiction or country. Accordingly, those people who choose to access the Services from other locations do so on their own initiative and are solely responsible for compliance with local laws, if and to the extent local laws are applicable.\n" +
                            "\n" +
                            "2. INTELLECTUAL PROPERTY RIGHTS\n" +
                            "Our intellectual property\n" +
                            "We are the owner or the licensee of all intellectual property rights in our Services, including all source code, databases, functionality, software, website designs, audio, video, text, photographs, and graphics in the Services (collectively, the \"Content\"), as well as the trademarks, service marks, and logos contained therein (the \"Marks\").\n" +
                            "Our Content and Marks are protected by copyright and trademark laws (and various other intellectual property rights and unfair competition laws) and treaties in the United States and around the world.\n" +
                            "The Content and Marks are provided in or through the Services \"AS IS\" for your personal, non-commercial use or internal business purposes only.\n" +
                            "Your use of our Services\n" +
                            "Subject to your compliance with these Legal Terms, including the \"PROHIBITED ACTIVITIES\" section below, we grant you a non-exclusive, non-transferable, revocable license to:\n" +
                            "• access the Services; and\n" +
                            "• download or print a copy of any portion of the Content to which you have properly gained access.\n" +
                            "solely for your personal, non-commercial use or internal business purposes.\n" +
                            "Except as set out in this section or elsewhere in our Legal Terms, no part of the Services and no Content or Marks may be copied, reproduced, aggregated, republished, uploaded, posted, publicly displayed, encoded, translated, transmitted, distributed, sold, licensed, or otherwise exploited for any commercial purpose whatsoever, without our express prior written permission.\n" +
                            "If you wish to make any use of the Services, Content, or Marks other than as set out in this section or elsewhere in our Legal Terms, please address your request to: gezreelwazarcon@gmail.com. If we ever grant you the permission to post, reproduce, or publicly display any part of our Services or Content, you must identify us as the owners or licensors of the Services, Content, or Marks and ensure that any copyright or proprietary notice appears or is visible on posting, reproducing, or displaying our Content.\n" +
                            "We reserve all rights not expressly granted to you in and to the Services, Content, and Marks.\n" +
                            "Any breach of these Intellectual Property Rights will constitute a material breach of our Legal Terms and your right to use our Services will terminate immediately.\n" +
                            "Your submissions\n" +
                            "Please review this section and the \"PROHIBITED ACTIVITIES\" section carefully prior to using our Services to understand the (a) rights you give us and (b) obligations you have when you post or upload any content through the Services.\n" +
                            "Submissions: By directly sending us any question, comment, suggestion, idea, feedback, or other information about the Services (\"Submissions\"), you agree to assign to us all intellectual property rights in such Submission. You agree that we shall own this Submission and be entitled to its unrestricted use and dissemination for any lawful purpose, commercial or otherwise, without acknowledgment or compensation to you.\n" +
                            "You are responsible for what you post or upload: By sending us Submissions through any part of the Services you:\n" +
                            "•\tconfirm that you have read and agree with our \"PROHIBITED ACTIVITIES\" and will not post, send, publish, upload, or transmit through the Services any Submission that is illegal, harassing, hateful, harmful, defamatory, obscene, bullying, abusive, discriminatory, threatening to any person or group, sexually explicit, false, inaccurate, deceitful, or misleading;\n" +
                            "•\tto the extent permissible by applicable law, waive any and all moral rights to any such Submission;\n" +
                            "•\twarrant that any such Submission are original to you or that you have the necessary rights and licenses to submit such Submissions and that you have full authority to grant us the above-mentioned rights in relation to your Submissions; and\n" +
                            "•\twarrant and represent that your Submissions do not constitute confidential information.\n" +
                            "You are solely responsible for your Submissions and you expressly agree to reimburse us for any and all losses that we may suffer because of your breach of (a) this section, (b) any third party's intellectual property rights, or (c) applicable law.\n" +
                            "\n" +
                            "3. USER REPRESENTATIONS\n" +
                            "By using the Services, you represent and warrant that: (I ) all registration information you submit will be true, accurate, current, and complete; (2) you will maintain the accuracy of such information and promptly update such registration information as necessary; (3) you have the legal capacity and you agree to comply with these Legal Terms; (4) you are not a minor in the jurisdiction in which you reside, or if a minor, you have received parental permission to use the Services; (5) you will not access the Services through automated or non-human means, whether through a bot, script or otherwise; (6) you will not use the Services for any illegal or unauthorized purpose; and (7) your use of the Services will not violate any applicable law or regulation. If you provide any information that is untrue, inaccurate, not current, or incomplete, we have the right to suspend or terminate your account and refuse any and all current or future use of the Services (or any portion thereof).\n" +
                            "\n" +
                            "4. USER REGISTRATION\n" +
                            "You may be required to register to use the Services. You agree to keep your password confidential and will be responsible for all use of your account and password. We reserve the right to remove, reclaim, or change a username you select if we determine, in our sole discretion, that such username is inappropriate, obscene, or otherwise objectionable.\n" +
                            "5. PROHIBITED ACTIVITIES\n" +
                            "You may not access or use the Services for any purpose other than that for which we make the Services available. The Services may not be used in connection with any commercial endeavors except those that are specifically endorsed or approved by us.\n" +
                            "As a user of the Services, you agree not to:\n" +
                            "•\tSystematically retrieve data or other content from the Services to create or compile, directly or indirectly, a collection, compilation, database, or directory without written permission from us.\n" +
                            "•\tTrick, defraud, or mislead us and other users, especially in any attempt to learn sensitive account information such as user passwords.\n" +
                            "•\tCircumvent, disable, or otherwise interfere with security-related features of the Services, including features that prevent or restrict the use or copying of any Content or enforce limitations on the use of the Services and/or the Content contained therein.\n" +
                            "•\tDisparage, tarnish, or otherwise harm, in our opinion, us and/or the Services.\n" +
                            "•\tUse any information obtained from the Services in order to harass, abuse, or harm another person.\n" +
                            "•\tMake improper use of our support services or submit false reports of abuse or misconduct.\n" +
                            "•\tUse the Services in a manner inconsistent with any applicable laws or regulations.\n" +
                            "•\tEngage in unauthorized framing of or linking to the Services.\n" +
                            "•\tUpload or transmit (or attempt to upload or to transmit) viruses, Trojan horses, or other material, including excessive use of capital letters and spamming (continuous posting of repetitive text), that interferes with any party's uninterrupted use and enjoyment of the Services or modifies, impairs, disrupts, alters, or interferes with the use, features, functions, operation, or maintenance of the Services.\n" +
                            "•\tEngage in any automated use of the system, such as using scripts to send comments or messages, or using any data mining, robots, or similar data gathering and extraction tools.\n" +
                            "•\tDelete the copyright or other proprietary rights notice from any Content.\n" +
                            "•\tAttempt to impersonate another user or person or use the username of another user.\n" +
                            "•\tUpload or transmit (or attempt to upload or to transmit) any material that acts as a passive or active information collection or transmission mechanism, including without limitation, clear graphics interchange formats (\"gifs\"), IXI pixels, web bugs, cookies, or other similar devices (sometimes referred to as \"spyware\" or \"passive collection mechanisms\" or \"pcms\").\n" +
                            "•\tInterfere with, disrupt, or create an undue burden on the Services or the networks or services connected to the Services.\n" +
                            "•\tHarass, annoy, intimidate, or threaten any of our employees or agents engaged in providing any portion of the Services to you.\n" +
                            "•\tAttempt to bypass any measures of the Services designed to prevent or restrict access to the Services, or any portion of the Services.\n" +
                            "•\tCopy or adapt the Services' software, including but not limited to Flash, PHP, HTML, JavaScript, or other code.\n" +
                            "•\tExcept as permitted by applicable law, decipher, decompile, disassemble, or reverse engineer any of the software comprising or in any way making up a part of the Services.\n" +
                            "•\tExcept as may be the result of standard search engine or Internet browser usage, use, launch, develop, or distribute any automated system, including without limitation, any spider, robot, cheat utility, scraper, or offline reader that accesses the Services, or use or launch any unauthorized script or other software.\n" +
                            "•\tUse a buying agent or purchasing agent to make purchases on the Services.\n" +
                            "•\tMake any unauthorized use of the Services, including collecting usernames and/or email addresses of users by electronic or other means for the purpose of sending unsolicited email, or creating user accounts by automated means or under false pretenses.\n" +
                            "•\tUse the Services as part of any effort to compete with us or otherwise use the Services and/or the Content for any revenue-generating endeavor or commercial enterprise.\n" +
                            "•\tUse the Services to advertise or offer to sell goods and services.\n" +
                            "•\tSell or otherwise transfer your profile.\n" +
                            "\n" +
                            "6. USER GENERATED CONTRIBUTIONS\n" +
                            "The Services does not offer users to submit or post content.\n" +
                            "\n" +
                            "7. CONTRIBUTION LICENSE\n" +
                            "You and Services agree that we may access, store, process, and use any information and personal data that you provide following the terms of the Privacy Policy and your choices (including settings).\n" +
                            "By submitting suggestions or other feedback regarding the Services, you agree that we can use and share such feedback for any purpose without compensation to you.\n" +
                            "8. MOBILE APPLICATION LICENSE\n" +
                            "Use License \n" +
                            "If you access the Services via the App, then we grant you a revocable, non-exclusive, non-transferable, limited right to install and use the App on wireless electronic devices owned or controlled by you, and to access and use the App on such devices strictly in accordance with the terms and conditions of this mobile application license contained in these Legal Terms. You shall not: (I ) except as permitted by applicable law, decompile, reverse engineer, disassemble, attempt to derive the source code of, or decrypt the App; (2) make any modification, adaptation, improvement, enhancement, translation, or derivative work from the App; (3) violate any applicable laws, rules, or regulations in connection with your access or use of the App; (4) remove, alter, or obscure any proprietary notice (including any notice of copyright or trademark) posted by us or the licensors of the App; (5) use the App for any revenue-generating endeavor, commercial enterprise, or other purpose for which it is not designed or intended; (6) make the App available over a network or other environment permitting access or use by multiple devices or users at the same time; (7) use the App for creating a product, service, or software that is, directly or indirectly, competitive with or in any way a substitute for the App; (8) use the App to send automated queries to any website or to send any unsolicited commercial email; or (9) use any proprietary information or any of our interfaces or our other intellectual property in the design, development, manufacture, licensing, or distribution of any applications, accessories, or devices for use with the App.\n" +
                            "Apple and Android Devices\n" +
                            "The following terms apply when you use the App obtained from either the Apple Store or Google Play (each an \"App Distributor\") to access the Services: (1) the license granted to you for our App is limited to a non-transferable license to use the application on a device that utilizes the Apple iOS or Android operating systems, as applicable, and in accordance with the usage rules set forth in the applicable App Distributor's terms of service; (2) we are responsible for providing any maintenance and support services with respect to the App as specified in the terms and conditions of this mobile application license contained in these Legal Terms or as otherwise required under applicable law, and you acknowledge that each App Distributor has no obligation whatsoever to furnish any maintenance and support services with respect to the App; (3) in the event of any failure of the App to conform to any applicable warranty, you may notify the applicable App Distributor, and the App Distributor, in accordance with its terms and policies, may refund the purchase price, if any, paid for the App, and to the maximum extent permitted by applicable law, the App Distributor will have no other warranty obligation whatsoever with respect to the App; (4) you represent and warrant that (i) you are not located in a country that is subject to a US government embargo, or that has been designated by the US government as a \"terrorist supporting\" country and (ii) you are not listed on any US government list of prohibited or restricted parties; (5) you must comply with applicable third-party terms of agreement when using the App, e.g., if you have a VoIP application, then you must not be in violation of their wireless data service agreement when using the App; and (6) you acknowledge and agree that the App Distributors are third-party beneficiaries of the terms and conditions in this mobile application license contained in these Legal Terms, and that each App Distributor will have the right (and will be deemed to have accepted the right) to enforce the terms and conditions in this mobile application license contained in these Legal Terms against you as a third-party beneficiary thereof.\n" +
                            "9. SOCIAL MEDIA\n" +
                            "As part of the functionality of the Services, you may link your account with online accounts you have with third-party service providers (each such account, a \"Third-Party Account\") by either: (1 ) providing your Third-Party Account login information through the Services; or (2) allowing us to access your Third-Party Account, as is permitted under the applicable terms and conditions that govern your use of each Third-Party Account. You represent and warrant that you are entitled to disclose your Third-Party Account login information to us and/or grant us access to your Third-Party Account, without breach by you of any of the terms and conditions that govern your use of the applicable Third-Party Account, and without obligating us to pay any fees or making us subject to any usage limitations imposed by the third-party service provider of the Third-Party Account. By granting us access to any Third-Party Accounts, you understand that (I ) we may access, make available, and store (if applicable) any content that you have provided to and stored in your Third-Party Account (the \"Social Network Content\") so that it is available on and through the Services via your account, including without limitation any friend lists and (2) we may submit to and receive from your Third-Party Account additional information to the extent you are notified when you link your account with the Third-Party Account. Depending on the Third-Party Accounts you choose and subject to the privacy settings that you have set in such Third-Party Accounts, personally identifiable information that you post to your Third-Party Accounts may be available on and through your account on the Services. Please note that if a Third-Party Account or associated service becomes unavailable or our access to such Third-Party Account is terminated by the third-party service provider, then Social Network Content may no longer be available on and through the Services. You will have the ability to disable the connection between your account on the Services and your Third-Party Accounts at any time. PLEASE NOTE THAT YOUR RELATIONSHIP WITH THE THIRD-PARTY SERVICE PROVIDERS ASSOCIATED WITH YOUR THIRD-PARTY ACCOUNTS IS GOVERNED SOLELY BY YOUR AGREEMENT(S) WITH SUCH THIRD-PARTY SERVICE PROVIDERS. We make no effort to review any Social Network Content for any purpose, including but not limited to, for accuracy, legality, or non-infringement, and we are not responsible for any Social Network Content. You acknowledge and agree that we may access your email address book associated with a Third-Party Account and your contacts list stored on your mobile device or tablet computer solely for purposes of identifying and informing you of those contacts who have also registered to use the Services. You can deactivate the connection between the Services and your Third-Party Account by contacting us using the contact information below or through your account settings (if applicable). We will attempt to delete any information stored on our servers that was obtained through such Third-Party Account, except the username and profile picture that become associated with your account.\n" +
                            "\n" +
                            "10. ADVERTISERS\n" +
                            "We allow advertisers to display their advertisements and other information in certain areas of the Services, such as sidebar advertisements or banner advertisements. We simply provide the space to place such advertisements, and we have no other relationship with advertisers.\n" +
                            "\n" +
                            "\n" +
                            "11. SERVICES MANAGEMENT\n" +
                            "We reserve the right, but not the obligation, to: (I) monitor the Services for violations of these Legal Terms; (2) take appropriate legal action against anyone who, in our sole discretion, violates the law or these Legal Terms, including without limitation, reporting such user to law enforcement authorities; (3) in our sole discretion and without limitation, refuse, restrict access to, limit the availability of, or disable (to the extent technologically feasible) any of your Contributions or any portion thereof; (4) in our sole discretion and without limitation, notice, or liability, to remove from the Services or otherwise disable all files and content that are excessive in size or are in any way burdensome to our systems; and (5) otherwise manage the Services in a manner designed to protect our rights and property and to facilitate the proper functioning of the Services.\n" +
                            "\n" +
                            "12. PRIVACY POLICY\n" +
                            "We care about data privacy and security. Please review our Privacy Policy. By using the Services, you agree to be bound by our Privacy Policy, which is incorporated into these Legal Terms. Please be advised the Services are hosted in the Philippines. If you access the Services from any other region of the world with laws or other requirements governing personal data collection, use, or disclosure that differ from applicable laws in the Philippines, then through your continued use of the Services, you are transferring your data to the Philippines, and you expressly consent to have your data transferred to and processed in the Philippines.\n" +
                            "\n" +
                            "13. TERM AND TERMINATION\n" +
                            "These Legal Terms shall remain in full force and effect while you use the Services. WITHOUT LIMITING ANY OTHER PROVISION OF THESE LEGAL TERMS, WE RESERVE THE RIGHT TO, IN OUR SOLE DISCRETION AND WITHOUT NOTICE OR LIABILITY DENY ACCESS TO AND USE OF THE SERVICES (INCLUDING BLOCKING CERTAIN IP ADDRESSES), TO ANY PERSON FOR ANY REASON OR FOR NO REASON, INCLUDING WITHOUT LIMITATION FOR BREACH OF ANY REPRESENTATION, WARRANTY OR COVENANT CONTAINED IN THESE LEGAL TERMS OR OF ANY APPLICABLE LAW OR REGULATION. WE MAY TERMINATE YOUR USE OR PARTICIPATION IN THE SERVICES OR DELETE YOUR ACCOUNT AND ANY CONTENT OR INFORMATION THAT YOU POSTED AT ANY TIME, WITHOUT WARNING, IN OUR SOLE DISCRETION.\n" +
                            "If we terminate or suspend your account for any reason, you are prohibited from registering and creating a new account under your name, a fake or borrowed name, or the name of any third party, even if you may be acting on behalf of the third party. In addition to terminating or suspending your account, we reserve the right to take appropriate legal action, including without limitation pursuing civil, criminal, and injunctive redress.\n" +
                            "\n" +
                            "\n" +
                            "14. MODIFICATIONS AND INTERRUPTIONS\n" +
                            "We reserve the right to change, modify, or remove the contents of the Services at any time or for any reason at our sole discretion without notice. However, we have no obligation to update any information on our Services. We will not be liable to you or any third party for any modification, price change, suspension, or discontinuance of the Services.\n" +
                            "We cannot guarantee the Services will be available at all times. We may experience hardware, software, or other problems or need to perform maintenance related to the Services, resulting in interruptions, delays, or errors. We reserve the right to change, revise, update, suspend, discontinue, or otherwise modify the Services at any time or for any reason without notice to you. You agree that we have no liability whatsoever for any loss, damage, or inconvenience caused by your inability to access or use the Services during any downtime or discontinuance of the Services. Nothing in these Legal Terms will be construed to obligate us to maintain and support the Services or to supply any corrections, updates, or releases in connection therewith.\n" +
                            "\n" +
                            "15. GOVERNING LAW\n" +
                            "These Legal Terms shall be governed by and defined following the laws of the Philippines. QuadroZENlX and yourself irrevocably consent that the courts of the Philippines shall have exclusive jurisdiction to resolve any dispute which may arise in connection with these Legal Terms.\n" +
                            "\n" +
                            "16. DISPUTE RESOLUTION\n" +
                            "You agree to irrevocably submit all disputes related to these Legal Terms or the legal relationship established by these Legal Terms to the jurisdiction of the Philippines courts. QuadroZENIX shall also maintain the right to bring proceedings as to the substance of the matter in the courts of the country where you reside or, if these Legal Terms are entered into in the course of your trade or profession, the state of your principal place of business.\n" +
                            "\n" +
                            "17. CORRECTIONS\n" +
                            "There may be information on the Services that contains typographical errors, inaccuracies, or omissions, including descriptions, pricing, availability, and various other information. We reserve the right to correct any errors, inaccuracies, or omissions and to change or update the information on the Services at any time, without prior notice.\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "18. DISCLAIMER\n" +
                            "THE SERVICES ARE PROVIDED ON AN AS-IS AND AS-AVAILABLE BASIS. YOU AGREE THAT YOUR USE OF THE SERVICES WILL BE AT YOUR SOLE RISK. TO THE FULLEST EXTENT PERMITTED BY LAW, WE DISCLAIM ALL WARRANTIES, EXPRESS OR IMPLIED, IN CONNECTION WITH THE SERVICES AND YOUR USE THEREOF, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NON-INFRINGEMENT. WE MAKE NO WARRANTIES OR REPRESENTATIONS ABOUT THE ACCURACY OR COMPLETENESS OF THE SERVICES' CONTENT OR THE CONTENT OF ANY WEBSITES OR MOBILE APPLICATIONS LINKED TO THE SERVICES AND WE WILL ASSUME NO LIABILITY OR RESPONSIBILITY FOR ANY (1 ) ERRORS, MISTAKES, OR INACCURACIES OF CONTENT AND MATERIALS, (2) PERSONAL INJURY OR PROPERTY DAMAGE, OF ANY NATURE WHATSOEVER, RESULTING FROM YOUR ACCESS TO AND USE OF THE SERVICES, (3) ANY UNAUTHORIZED ACCESS TO OR USE OF OUR SECURE SERVERS AND/OR ANY AND ALL PERSONAL INFORMATION AND/OR FINANCIAL INFORMATION STORED THEREIN, (4) ANY INTERRUPTION OR CESSATION OF TRANSMISSION TO OR FROM THE SERVICES, (5) ANY BUGS, VIRUSES, TROJAN HORSES, OR THE LIKE WHICH MAY BE TRANSMITTED TO OR THROUGH THE SERVICES BY ANY THIRD PARTY, AND/OR (6) ANY ERRORS OR OMISSIONS IN ANY CONTENT AND MATERIALS OR FOR ANY LOSS OR DAMAGE OF ANY KIND INCURRED AS A RESULT OF THE USE OF ANY CONTENT POSTED, TRANSMITTED, OR OTHERWISE MADE AVAILABLE VIA THE SERVICES. WE DO NOT WARRANT, ENDORSE, GUARANTEE, OR ASSUME RESPONSIBILITY FOR ANY PRODUCT OR SERVICE ADVERTISED OR OFFERED BY A THIRD PARTY THROUGH THE SERVICES, ANY HYPERLINKED WEBSITE, OR ANY WEBSITE OR MOBILE APPLICATION FEATURED IN ANY BANNER OR OTHER ADVERTISING, AND WE WILL NOT BE A PARTY TO OR IN ANY WAY BE RESPONSIBLE FOR MONITORING ANY TRANSACTION BETWEEN YOU AND ANY THIRD-PARTY PROVIDERS OF PRODUCTS OR SERVICES. AS WITH THE PURCHASE OF A PRODUCT OR SERVICE THROUGH ANY MEDIUM OR IN ANY ENVIRONMENT, YOU SHOULD USE YOUR BEST JUDGMENT AND EXERCISE CAUTION WHERE APPROPRIATE.\n" +
                            "\n" +
                            "19. LIMITATIONS OF LIABILITY\n" +
                            "IN NO EVENT WILL WE OR OUR DIRECTORS, EMPLOYEES, OR AGENTS BE LIABLE TO YOU OR ANY THIRD PARTY FOR ANY DIRECT, INDIRECT, CONSEQUENTIAL, EXEMPLARY INCIDENTAL, SPECIAL, OR PUNITIVE DAMAGES, INCLUDING LOST PROFIT, LOST REVENUE, LOSS OF DATA, OR OTHER DAMAGES ARISING FROM YOUR USE OF THE SERVICES, EVEN IF WE HAVE BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.\n" +
                            "\n" +
                            "20. INDEMNIFICATION\n" +
                            "You agree to defend, indemnify, and hold us harmless, including our subsidiaries, affiliates, and all of our respective officers, agents, partners, and employees, from and against any loss, damage, liability, claim, or demand, including reasonable attorneys' fees and expenses, made by any third party due to or arising out of: (I ) use of the Services; (2) breach of these Legal Terms; (3) any breach of your representations and warranties set forth in these Legal Terms; (4) your violation of the rights of a third party, including but not limited to intellectual property rights; or (5) any overt harmful act toward any other user of the Services with whom you connected via the Services. Notwithstanding the foregoing, we reserve the right, at your expense, to assume the exclusive defense and control of any matter for which you are required to indemnify us, and you agree to cooperate, at your expense, with our defense of such claims. We will use reasonable efforts to notify you of any such claim, action, or proceeding which is subject to this indemnification upon becoming aware of it.\n" +
                            "\n" +
                            "21. USER DATA\n" +
                            "We will maintain certain data that you transmit to the Services for the purpose of managing the performance of the Services, as well as data relating to your use of the Services. Although we perform regular routine backups of data, you are solely responsible for all data that you transmit or that relates to any activity you have undertaken using the Services. You agree that we shall have no liability to you for any loss or corruption of any such data, and you hereby waive any right of action against us arising from any such loss or corruption of such data.\n" +
                            "\n" +
                            "22. ELECTRONIC COMMUNICATIONS, TRANSACTIONS, AND SIGNATURES\n" +
                            "Visiting the Services, sending us emails, and completing online forms constitute electronic communications. You consent to receive electronic communications, and you agree that all agreements, notices, disclosures, and other communications we provide to you electronically, via email and on the Services, satisfy any legal requirement that such communication be in writing. YOU HEREBY AGREE TO THE USE OF ELECTRONIC SIGNATURES, CONTRACTS, ORDERS, AND OTHER RECORDS, AND TO ELECTRONIC DELIVERY OF NOTICES, POLICIES, AND RECORDS OF TRANSACTIONS INITIATED OR COMPLETED BY US OR VIA THE SERVICES. You hereby waive any rights or requirements under any statutes, regulations, rules, ordinances, or other laws in any jurisdiction which require an original signature or delivery or retention of non-electronic records, or to payments or the granting of credits by any means other than electronic means.\n" +
                            "\n" +
                            "23. MISCELLANEOUS\n" +
                            "These Legal Terms and any policies or operating rules posted by us on the Services or in respect to the Services constitute the entire agreement and understanding between you and us. Our failure to exercise or enforce any right or provision of these Legal Terms shall not operate as a waiver of such right or provision. These Legal Terms operate to the fullest extent permissible by law. We may assign any or all of our rights and obligations to others at any time. We shall not be responsible or liable for any loss, damage, delay, or failure to act caused by any cause beyond our reasonable control. If any provision or part of a provision of these Legal Terms is determined to be unlawful, void, or unenforceable, that provision or part of the provision is deemed severable from these Legal Terms and does not affect the validity and enforceability of any remaining provisions. There is no joint venture, partnership, employment or agency relationship created between you and us as a result of these Legal Terms or use of the Services. You agree that these Legal Terms will not be construed against us by virtue of having drafted them. You hereby waive any and all defenses you may have based on the electronic form of these Legal Terms and the lack of signing by the parties hereto to execute these Legal Terms.\n" +
                            "\n" +
                            "24. CONTACT US\n" +
                            "In order to resolve a complaint regarding the Services or to receive further information regarding use of the Services, please contact us at:\n" +
                            "QuadroZENlX\n" +
                            "Anonas cor., Pureza Street\n" +
                            "Sta. Mesa, Manila, Metro Manila 1016\n" +
                            "Philippines\n" +
                            "Phone: 0123-456-789\n" +
                            "gezreelwazarcon@gmail.com\n");
                    materialAlertDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            create.setEnabled(true);
                            dialogInterface.dismiss();

                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            check.setChecked(false);

                        }
                    });

                    materialAlertDialogBuilder.show();
                }else {
                    create.setEnabled(true);


                }

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSigninPage();

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString();
                String txtPass = password.getText().toString();
                String txtemail = email.getText().toString();
                String txtnickname = nickname.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPass) && check.isChecked()) {
                    Toast.makeText(SignupPage.this, "Credentials are Empty!", Toast.LENGTH_SHORT).show();
                }else if (!check.isChecked()){
                    Toast.makeText(SignupPage.this, "Accept Terms and Conditions!", Toast.LENGTH_SHORT).show();
                }else if (txtPass.length() < 6){
                    Toast.makeText(SignupPage.this, "Password should be greater than 6", Toast.LENGTH_SHORT).show();
                }else if (txtPass.length() > 20){
                    Toast.makeText(SignupPage.this, "Password should be less than 20", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(txtEmail, txtPass, txtnickname, txtemail);
                    myDatabase.resetLocalDatabase();
                    myDatabase.resetLocalHistoryDatabase();
                }
            }
        });




    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GĀDO App");
        builder.setMessage("What is GĀDO?\n" +
                "Inspired by the Japanese pronunciation of the word guard, GĀDO is a game-based and self-help Android application that aims to solve the budgeting problems of its users. It aims to help its users build budgeting skills and discipline on their own. The application targets audiences who are having a hard time maintaining and keeping track of their budget.\n" +
                "\n" +
                "How to use it?\n" +
                "The application will ask for the user’s daily budget and uses it as the game's life/health points, and the goods, foods, commodities, etc. that are bought by the user are considered the enemy. The app has a simple goal in mind, it is to “guard” the game’s life which is your budget.\n" +
                "\n" +
                "How will the user benefit?\n" +
                "The audience will benefit from the application by being reassured and by having their budget maintained and monitored, it may also help them self-develop budgeting skills and discipline as it is a self-help application. It is an app that is game-based so it might give positivity and enjoyment instead of stress and anxiety, it can also help them reevaluate their current budget for the optimal ‘game outcome’ that would directly translate to optimal budgeting in real life.");
        builder.setPositiveButton("Skip", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                create.setEnabled(true);
                dialogInterface.dismiss();

            }
        });
        builder.create().show();

    }

    private void registerUser(String email, String pass, String txtnickname, String txtemail) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignupPage.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupPage.this, "User Registration Successful!", Toast.LENGTH_SHORT).show();
                    Paper.book().write("UserEmail", email);
                    Paper.book().write("UserPass", pass);
                    openInputPage();
                    finish();
                }else{
                    Toast.makeText(SignupPage.this, "User Registration Failed! Check Credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openSigninPage(){
        Intent intent = new Intent(this, SigninPage.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openInputPage() {
        Intent intent = new Intent(this, WelcomeuserPage.class);
        startActivity(intent);
        finish();
    }

    public void openVerificationpage(String txtnickname, String txtemail){
        Intent intent = new Intent(this, VerificationPage.class);
        intent.putExtra("keytxtnickname", txtnickname);
        intent.putExtra("keytxtemail", txtemail);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }




}


