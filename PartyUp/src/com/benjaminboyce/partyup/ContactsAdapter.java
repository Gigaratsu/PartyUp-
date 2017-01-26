package com.benjaminboyce.partyup;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<PartyMember>{
	
	public ContactsAdapter(Context context, ArrayList<PartyMember> members){
		super(context, 0, members);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		//Get the data item for this position
		PartyMember member = getItem(position);
		//Check if an exiting view is being reused, otherwise inflate the view
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, parent, false);
		}
		//Lookup view for data population
		TextView tvName = (TextView)convertView.findViewById(R.id.contactNameView);
		TextView tvNumber = (TextView)convertView.findViewById(R.id.contactPhoneView);
		//Populate the data into the template view using the data object
		tvName.setText(member.getName());
		tvNumber.setText(member.getNumber());
		return convertView;		
	}

}
