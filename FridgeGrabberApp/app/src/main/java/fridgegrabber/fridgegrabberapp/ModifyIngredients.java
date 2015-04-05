package fridgegrabber.fridgegrabberapp;

/**
 * Created by Stephen on 4/5/2015.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;



public class ModifyIngredients extends Activity implements RemoveTagDialogFragment.RemoveTagDialogFragmentListener{

    ArrayList<TextView> ingredientTags;
    PredicateLayout predLayout;

    private static final int REMOVE_TAG_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ingredients);

        ingredientTags = new ArrayList<>();

        final EditText addIngredientsEditText = (EditText) findViewById(R.id.edittextingredients);


        predLayout = (PredicateLayout) findViewById(R.id.predicate_layout);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final String ingredientName = addIngredientsEditText.getText().toString();

                // if something was entered into the edittext box and it only had alphabetic characters
                if (!ingredientName.isEmpty() && ingredientName.matches("[a-zA-Z]+"))
                {
                    //create new textview visual "tag" in the PredicateLayout
                    TextView tag = new TextView(ModifyIngredients.this);
                    tag.setText(ingredientName);
                    tag.setBackgroundColor(Color.CYAN);
                    tag.setSingleLine(false);
                    predLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


                    // set onclicklistener such that if user presses the textview, prompts the user with dialog
                    // to remove the "tag" or ingredient from the list of ingredient tags
                    tag.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v)
                        {
                            RemoveTagDialogFragment dfrag = new RemoveTagDialogFragment();
                            dfrag.ingredientName = ingredientName;
                            dfrag.setTargetFragment(dfrag, REMOVE_TAG_REQUEST_CODE);
                            dfrag.show(getFragmentManager().beginTransaction(), "dialog");
                        }
                    });

                    // add to arrayList of textviews representing the ingredient tags
                    ingredientTags.add(tag);

                    // reset input box to empty
                    addIngredientsEditText.setText("");
                }




            }
        });

    }

    // implementing interface method in RemoveTagDialogFragment
    // basically if resultcode is RESULT_OK -> means that user pressed DELETE in the dialog box
    // removes the item from the list that is named ingredientName
    public void onFinishRemoveTagDialogFragment(int resultCode, String ingredientName)
    {
        // if RESULT_OK then remove the corresponding view in the PredicateLayout
        // and the corresponding TextView in the ArrayList
        if (resultCode == Activity.RESULT_OK)
        {
            for (int i = 0; i < ingredientTags.size(); i++)
            {
                // if the current TextView "tag" is the ingredient tag we're looking for
                if (ingredientTags.get(i).getText().toString().equals(ingredientName))
                {
                    predLayout.removeView(ingredientTags.get(i));
                    ingredientTags.remove(i);

                }

            }

        }

    }


}
