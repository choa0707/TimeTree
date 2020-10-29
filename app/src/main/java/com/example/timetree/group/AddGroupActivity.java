package com.example.timetree.group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetree.EventItem;
import com.example.timetree.R;
import com.example.timetree.RecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGroupActivity extends AppCompatActivity {
    String add_id;
    String get_id;
    String filename;
    int success = 0;
    private Uri filePath;
    RecyclerAdapter recyclerAdapter;
    EditText editText, group_name;
    Button searchButton, groupRegist, group_image_search;
    TextView group_image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        group_image_search = (Button)findViewById(R.id.group_image);
        group_image_url = (TextView)findViewById(R.id.group_image_url);
        group_name = (EditText)findViewById(R.id.group_name);
        editText = findViewById(R.id.user_add_edittext);
        searchButton = findViewById(R.id.user_search_button);
        groupRegist = findViewById(R.id.group_regist);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.user_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(recyclerAdapter);

        group_image_search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        groupRegist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (recyclerAdapter.getItemCount() == 0)
                {
                    Toast.makeText(getApplicationContext(), "멤버를 선택해주세요.",Toast.LENGTH_LONG).show();
                }
                else if (group_name.getText().toString() == null || group_name.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "그룹 이름을 설정해 주세요.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    uploadFile();
                    DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference().child("Groups");
                    DatabaseReference newGroupRef = groupRef.push();

                    GroupInfo groupInfo = new GroupInfo(group_name.getText().toString(), filename);
                    newGroupRef.setValue(groupInfo);

                    DatabaseReference groupMemberRef = newGroupRef.child("members");
                    for (int i = 0; i < recyclerAdapter.getItemCount(); i++)
                    {
                        GroupMember groupMember = new GroupMember(recyclerAdapter.getItem(i));
                        groupMemberRef.push().setValue(groupMember);
                    }
                    //////TODO: 일정추가
                    EventItem eventItem = new EventItem();
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    String eid = date.toString();
                    DatabaseReference eventsRef = newGroupRef.child("events");
                    eventsRef.child(eid).setValue(eventItem);
                    ///////////////////////////////////////

                    GroupMember groupMember = new GroupMember(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    groupMemberRef.push().setValue(groupMember);
                    Toast.makeText(getApplicationContext(), "등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                get_id = editText.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                success = 0;
                DatabaseReference myRef = database.getReference().child("users");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            for (DataSnapshot i : dataSnapshot.getChildren()) {
                                add_id = i.getValue().toString();

                                int s = add_id.indexOf("email");
                                int e = s;
                            if (s != -1)
                            {
                                for (int j = s; j < add_id.length(); j++)
                                {
                                    if (add_id.charAt(j) == ',')
                                    {
                                        e = j;
                                        break;
                                    }
                                }
                                add_id = add_id.substring(s+6,e);
                                Log.d("dbtest",add_id);
                                if (get_id.equals(add_id))
                                {
                                    success = 1;
                                    break;
                                }
                            }
                            //eventItems.add(i.getValue(EventItem.class));
                        }
                        if (success == 0)
                        {
                            Toast.makeText(getApplicationContext(), "해당 사용자가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            recyclerAdapter.addItem(get_id);
                            recyclerAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d("ImageUri", "uri:" + String.valueOf(filePath));

                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //ivPreview.setImageBitmap(bitmap);
                group_image_url.setText(String.valueOf(filePath));

        }
    }



    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://daltree-d7366.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        finish();
    }
}