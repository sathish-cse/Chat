package com.tech42.sathish.firebasechat.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tech42.sathish.firebasechat.R;
import com.tech42.sathish.firebasechat.model.ChatMessage;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;


public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ChatMessage> mChatList;
    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;

    public MessageChatAdapter(List<ChatMessage> listOfFireChats) {
        mChatList = listOfFireChats;
    }

    @Override
    public int getItemViewType(int position) {
        if(mChatList.get(position).getRecipientOrSenderStatus()==SENDER){
            return SENDER;
        }else {
            return RECIPIENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case SENDER:
                View viewSender = inflater.inflate(R.layout.layout_sender_message, viewGroup, false);
                viewHolder= new ViewHolderSender(viewSender);
                break;
            case RECIPIENT:
                View viewRecipient = inflater.inflate(R.layout.layout_recipient_message, viewGroup, false);
                viewHolder=new ViewHolderRecipient(viewRecipient);
                break;
            default:
                View viewSenderDefault = inflater.inflate(R.layout.layout_sender_message, viewGroup, false);
                viewHolder= new ViewHolderSender(viewSenderDefault);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()){
            case SENDER:
                ViewHolderSender viewHolderSender=(ViewHolderSender)viewHolder;
                configureSenderView(viewHolderSender,position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient=(ViewHolderRecipient)viewHolder;
                configureRecipientView(viewHolderRecipient,position);
                break;
        }


    }

    private void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        ChatMessage senderFireMessage= mChatList.get(position);
        boolean isPhoto = senderFireMessage.getImageurl() != null;
        if(isPhoto) {
            try {
                Bitmap image = decodeFromFirebaseBase64(senderFireMessage.getImageurl());
                viewHolderSender.getSenderMessageImageTextView().setImageBitmap(image);
                viewHolderSender.getSenderMessageTextView().setText(senderFireMessage.getMessage());
                viewHolderSender.getSenderTimeTextView().setText(senderFireMessage.getTime());
            }
            catch(IOException i)
            {}
        }
        else
            viewHolderSender.getSenderMessageTextView().setText(senderFireMessage.getMessage());
            viewHolderSender.getSenderTimeTextView().setText(senderFireMessage.getTime());

    }

    private void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        ChatMessage recipientFireMessage = mChatList.get(position);
        boolean isPhoto = recipientFireMessage.getImageurl() != null;
        if(isPhoto) {
            try{
            Bitmap image = decodeFromFirebaseBase64(recipientFireMessage.getImageurl());
                viewHolderRecipient.getRecipientMessageImageView().setImageBitmap(image);
                viewHolderRecipient.getRecipientMessageTextView().setText(recipientFireMessage.getMessage());
                viewHolderRecipient.getmRecipientTimeTextView().setText(recipientFireMessage.getTime());

            }
            catch(IOException i)
            {}
        }
        else
            viewHolderRecipient.getRecipientMessageTextView().setText(recipientFireMessage.getMessage());
            viewHolderRecipient.getmRecipientTimeTextView().setText(recipientFireMessage.getTime());

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }


    public void refillAdapter(ChatMessage newFireChatMessage){

        /*add new message chat to list*/
        mChatList.add(newFireChatMessage);

        /*refresh view*/
        notifyItemInserted(getItemCount()-1);
    }


    public void cleanUp() {
        mChatList.clear();
    }


    /*==============ViewHolder===========*/

    /*ViewHolder for Sender*/

    public class ViewHolderSender extends RecyclerView.ViewHolder {

        private TextView mSenderMessageTextView, mSenderTimeTextView;
        private ImageView msenderMessageImageView;

        public ViewHolderSender(View itemView) {
            super(itemView);
            mSenderMessageTextView = (TextView)itemView.findViewById(R.id.text_view_sender_message);
            msenderMessageImageView = (ImageView)itemView.findViewById(R.id.image_view_sender_message);
            mSenderTimeTextView = (TextView)itemView.findViewById(R.id.time_sender_message);
        }

        public TextView getSenderMessageTextView() {
            return mSenderMessageTextView;
        }

        public ImageView getSenderMessageImageTextView()
        {
            return msenderMessageImageView;
        }

        public TextView getSenderTimeTextView()
        {
            return mSenderTimeTextView;
        }
    }


    /*ViewHolder for Recipient*/
    public class ViewHolderRecipient extends RecyclerView.ViewHolder {

        private TextView mRecipientMessageTextView,mRecipientTimeTextView;
        private ImageView mRecipientMessageImageView;

        public ViewHolderRecipient(View itemView) {
            super(itemView);
            mRecipientMessageTextView=(TextView)itemView.findViewById(R.id.text_view_recipient_message);
            mRecipientMessageImageView = (ImageView)itemView.findViewById(R.id.image_view_recipient_message);
            mRecipientTimeTextView = (TextView)itemView.findViewById(R.id.time_recipient_message);
        }

        public TextView getRecipientMessageTextView() {
            return mRecipientMessageTextView;
        }

        public ImageView getRecipientMessageImageView() {
            return mRecipientMessageImageView;
        }

        public TextView getmRecipientTimeTextView()
        {
            return mRecipientTimeTextView;
        }

    }

    // String decode to bitmap
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}