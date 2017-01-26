package com.benjaminboyce.partyup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PartyPage extends Activity {
	
	private DatabaseHelper dbh;
	private ArrayList<String> memberNames;
	private ArrayList<PartyMember> members;
	private ArrayAdapter<String> ad;
	private ListView lv;
	
	public static final int PICK_CONTACT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_party_page);
		members = new ArrayList<PartyMember>();
		memberNames = new ArrayList<String>();
		lv = (ListView)findViewById(R.id.listMem);
		
		dbh = new DatabaseHelper(this);
		members = dbh.getPartyMembers();
		if(!members.isEmpty()){
			for(int i = 0; i < members.size(); i++){
				memberNames.add(members.get(i).getName());
			}
			ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, memberNames);
			lv.setAdapter(ad);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.party_page, menu);
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
	
	public void addNewMem(View view){
		Intent intent = new Intent(PartyPage.this, AddMembersFromContacts.class);
		this.startActivityForResult(intent, PICK_CONTACT);
	}
	
	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data){
		super.onActivityResult(reqCode, resCode, data);
		switch(reqCode){
			case PICK_CONTACT:
				Log.i("PartyUp - PartyPage","Bringing data from AddMembers and uploading to database");
				if (resCode == Activity.RESULT_OK) {
					PartyMember mem = new PartyMember();
					if(data.hasExtra("newMemName")){
						
						mem.setName(data.getExtras().getString("newMemName"));
					} else {
					}
					if(data.hasExtra("newMemPhone")){
						mem.setNumber(data.getExtras().getString("newMemPhone"));
					} else {
						Toast.makeText(this, "Could not retrieve contact's data", Toast.LENGTH_LONG).show();
					}
					dbh.addMem(mem);
			        }
				break;
		}
		refresh();
	}

	private void refresh() {
		members.clear();
		//memberNames.clear();
		dbh = new DatabaseHelper(this);
		members = dbh.getPartyMembers();
		if(!members.isEmpty()){
			for(int i = 0; i < members.size(); i++){
				memberNames.add(members.get(i).getName());
			}
			ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, memberNames);
			lv.setAdapter(ad);
		}
		
	}
}
