package com.benjaminboyce.partyup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AddMembersFromContacts extends Activity {
	
	public static final int MENU_ADD = Menu.FIRST+1;
	private AlertDialog.Builder alert;
	private ListView lv;
	private ArrayList<PartyMember> party;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_members_from_contacts);
		
		lv = (ListView) findViewById(R.id.listContacts);
		party = new ArrayList<PartyMember>();
		PartyMember mem;
		alert = new AlertDialog.Builder(this);
		alert.setTitle("Add to Party?");
		alert.setMessage("Do you wish to add this contact to your party?");

		Context mContext = this;
		ContentResolver cr = mContext.getContentResolver(); //Activity/Application android.content.Context
	    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
	    if(cursor.moveToFirst())
	    {
	        do
	        {
	            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
	            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

	            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
	            {
	                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
	                while (pCur.moveToNext()) 
	                {
	                	mem = new PartyMember();
	                    String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                    mem.setName(name);
	                    mem.setNumber(contactNumber);
	                    party.add(mem);
	                    break;
	                }
	                pCur.close();
	            }

	        } while (cursor.moveToNext()) ;
	       ContactsAdapter ad = new ContactsAdapter(mContext, party);
	       lv.setAdapter(ad);
	    }
	    registerForContextMenu(lv);
	    
	    
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

		menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "Add to party");
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterView.AdapterContextMenuInfo info=
			(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		switch (item.getItemId()) {
			case MENU_ADD:
				bringBack(info.position);
				return(true);
		}
		return(super.onContextItemSelected(item));
	}

	private void bringBack(int position) {
		//return the result of the activity
		Intent intent = new Intent(AddMembersFromContacts.this, PartyPage.class);
		intent.putExtra("newMemName", party.get(position).getName());
		intent.putExtra("newMemPhone", party.get(position).getNumber());
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, intent);
		} else {
		    getParent().setResult(Activity.RESULT_OK, intent);
		}
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_members_from_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
