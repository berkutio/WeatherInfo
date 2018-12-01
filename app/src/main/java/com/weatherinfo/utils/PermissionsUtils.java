package com.weatherinfo.utils;

import android.Manifest;
import android.app.Activity;
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

    private static final String SETTINGS_URI = "package:";

    public static boolean isPermissionGranted(Permissions permission) {
        int permissionCheck = ActivityCompat.checkSelfPermission(App.getAppContext(), permission.getTitle());
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity context, Permissions[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(context,
                convertPermissionsToStringArray(permissions), requestCode);
    }

    private static String[] convertPermissionsToStringArray(Permissions[] permissions) {
        String[] stringPermissions = new String[permissions.length];
        for (int i = 0; i < stringPermissions.length; i++) {
            stringPermissions[i] = permissions[i].getTitle();
        }

        return stringPermissions;
    }

    public static void showPermissionDialogForResult(final Activity activity, String permissionsDescription, final int requestCode) {
        AlertDialog dialog = createPermissionDialog(activity, permissionsDescription, requestCode);
        dialog.show();
    }

    private static AlertDialog createPermissionDialog(final Activity activity, String permissionsDescription, final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.app_name));
        builder.setMessage(activity.getString(R.string.app_name) + " " + permissionsDescription);

        String positiveText = activity.getString(R.string.settings);
        builder.setPositiveButton(positiveText, (dialog, which) -> openSettingsActivity(activity, requestCode));

        String negativeText = activity.getString(R.string.exit);
        builder.setNegativeButton(negativeText, (dialog, which) -> activity.finish());

        return builder.create();
    }

    private static void openSettingsActivity(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(SETTINGS_URI + activity.getPackageName()));

        activity.startActivityForResult(intent, requestCode);
    }

    public enum Permissions {
        ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION),
        ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION);

        private String title;

        Permissions(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }


}
