package com.movielate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

public class FireStore {
	Firestore db;
	public void connect() {
		FirestoreOptions firestoreOptions =
			    FirestoreOptions.getDefaultInstance().toBuilder()
			        .setProjectId("movelate-8a3ee")
			        .build();
			db = firestoreOptions.getService();
	}	
	public ArrayList<String> readData() throws InterruptedException, ExecutionException {			
					ArrayList<String> arrayList = new ArrayList<>();	
					// asynchronously retrieve all users
					ApiFuture<QuerySnapshot> query = db.collection("Flashcards").get();
					// query.get() blocks on response
					QuerySnapshot querySnapshot = query.get();
					List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
					for (QueryDocumentSnapshot document : documents) {
						arrayList.add(document.getString("eng"));
						arrayList.add(document.getString("pl"));			
					}
					return arrayList;
	}
	public void add(String eng, String pl) throws InterruptedException, ExecutionException {		
			DocumentReference docRef = db.collection("Flashcards").document();
			Map<String, Object> data = new HashMap<>();
			data.put("eng", eng);
			data.put("pl", pl);
			//asynchronously write data
			ApiFuture<WriteResult> result = docRef.set(data);
			// result.get() blocks on response
			System.out.println("Update time : " + result.get().getUpdateTime());
	}
}
