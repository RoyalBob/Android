package com.royalbob.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by RoyalBob on 2016/7/22.
 */
public class ImportContacts {

    private Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;// 联系人的Uri对象
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;// 获取联系人电话的Uri
    private Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;// 获取联系人邮箱的Uri
    private ContentValues content = new ContentValues();

    public void ImportContacts(Activity activity){
        ContentResolver contentResolver = activity.getContentResolver();
        // 获得所有的联系人
        Cursor cursor = contentResolver.query(contactsUri,null, null, null, null);
        while(cursor.moveToNext())
        {
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            content.put("姓名", contactName);

            Cursor phoneCursor = contentResolver.query(phoneUri,null, ContactsContract.Contacts.DISPLAY_NAME+"=?", new String[]{contactName+""}, null);
            while (phoneCursor.moveToNext()) {
                // 获取联系人电话号码
                content.put("电话", phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }
            phoneCursor.close();

            Cursor emailCursor = contentResolver.query(emailUri,null, ContactsContract.Contacts.DISPLAY_NAME+"=?", new String[]{contactName+""}, null);
            while (emailCursor.moveToNext()) {
                // 获取联系人电话号码
                content.put("地址", emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
            }
            emailCursor.close();
//            content.put("电话", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//            content.put("地址", cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
            Sqlite.mDb.insert("contacts", null, content);
        }
        cursor.close();

    }

}
