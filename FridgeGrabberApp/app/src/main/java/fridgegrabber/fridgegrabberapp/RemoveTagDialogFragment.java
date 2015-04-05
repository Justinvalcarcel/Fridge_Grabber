package fridgegrabber.fridgegrabberapp;

/**
 * Created by Stephen on 4/5/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Stephen on 4/4/2015.
 * This fragment returns True/False value to the activity which the dialogfragment object was created
 * depending on whether user presses "Delete" or "Cancel"
 */


public class RemoveTagDialogFragment extends DialogFragment {

    public interface RemoveTagDialogFragmentListener
    {
        public void onFinishRemoveTagDialogFragment(int resultCode, String ingredientName);

    }

    private RemoveTagDialogFragmentListener mListener;

    // name of ingredient to be removed
    public String ingredientName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Remove \"" + ingredientName +"\" from ingredients list?")

                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // call onFinishRemoveTagDialogFragment with RESULT_CANCELED
                        mListener.onFinishRemoveTagDialogFragment(Activity.RESULT_CANCELED, ingredientName);
                    }
                })

                        //pressing delete will remove the tag
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // call onFinishRemoveTagDialogFragment with RESULT_OK
                        mListener.onFinishRemoveTagDialogFragment(Activity.RESULT_OK, ingredientName);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    // Makes sure activity implemented the interface:
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            mListener = (RemoveTagDialogFragmentListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement RemoveTagDialogFragmentListener");
        }
    }

}
