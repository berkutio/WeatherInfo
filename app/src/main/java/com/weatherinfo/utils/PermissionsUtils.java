package com.weatherinfo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.weatherinfo.App;
import com.weatherinfo.R;

/**
 * Created by user on 26.04.17.
 */

public class PermissionsUtils {


    public static boolean isPermissionGranted(Permissions permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(App.getAppContext(), permission.getDesc());
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity context, Permissions[] permissionses, int requestCode){
        ActivityCompat.requestPermissions(context,
                convertPermissionsToString(permissionses), requestCode);
    }

    private static String[] convertPermissionsToString(Permissions[] permissionses){
        String[] permissions = new String[permissionses.length];
        for (int i = 0; i < permissions.length; i++) {
            permissions[i] = permissionses[i].getDesc();
        }
        return permissions;
    }

    public static void showPermissionDialogForResult(final Activity context, String permissionsDescription, final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name));
        builder.setMessage(context.getString(R.string.app_name) + " " + permissionsDescription);

        String positiveText = context.getString(R.string.settings);
        builder.setPositiveButton(positiveText, (dialog, which) -> openSettings(context, requestCode));

        String negativeText = context.getString(R.string.exit);
        builder.setNegativeButton(negativeText, (dialog, which) -> context.finish());

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private static void openSettings(Activity context, int requestCode){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, requestCode);
    }

    public enum Permissions{
        ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
        ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION);

        private String desc;

        Permissions(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }


}
