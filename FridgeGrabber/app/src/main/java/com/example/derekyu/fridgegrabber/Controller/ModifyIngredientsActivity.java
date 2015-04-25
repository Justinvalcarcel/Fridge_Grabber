package com.example.derekyu.fridgegrabber.Controller;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.derekyu.fridgegrabber.R;
import com.example.derekyu.fridgegrabber.tools.PredicateLayout;

public class ModifyIngredientsActivity extends Activity implements RemoveTagDialogFragment.RemoveTagDialogFragmentListener{

    Map<String, TextView> ingredientTags;
    PredicateLayout currentIngredPredLayout;

    List<String> commonDairyIngredients = Arrays.asList("milk", "frozen yogurt", "ice cream", "cheddar cheese", "mozzarella cheese", "Swiss cheese", "Parmesan cheese", "American cheese" );
    List<String> commonProteinIngredients = Arrays.asList("beef", "pork", "chicken", "duck", "turkey", "eggs", "black beans", "kidney beans", "lima beans", "tofu", "salmon", "tuna", "scallop");
    List<String> commonVegetablesIngredients = Arrays.asList("bok choy", "broccoli", "lettuce", "spinach", "watercress", "corn", "potatoes", "green peas", "tomatoes", "carrots", "green peppers", "red peppers", "cucumbers");
    List<String> commonFruitsIngredients = Arrays.asList("apples", "bananas", "cherries", "oranges", "pineapples" );

    private static final int REMOVE_TAG_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ingredients);


        // hashmap that holds string-textview key-value pairs
        ingredientTags = new HashMap<>();

        final EditText addIngredientsEditText = (EditText) findViewById(R.id.edittextingredients);


        currentIngredPredLayout = (PredicateLayout) findViewById(R.id.predicate_layout_current_ingred);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final String ingredientName = addIngredientsEditText.getText().toString();

                // if something was entered into the edittext box and it only had alphabetic characters
                if (!ingredientName.isEmpty() && ingredientName.matches("[a-zA-Z]+"))
                {
                    // if ingredient not in the current list, add it
                    if (!ingredientTags.containsKey(ingredientName))
                        addIngredientToCurrentList(ingredientName);
                    // display toast notification that adding ingredient failed
                    else
                        Toast.makeText(getApplicationContext(), "Failed to add " + ingredientName + " - it has already been added!", Toast.LENGTH_SHORT).show();

                    // reset input box to empty
                    addIngredientsEditText.setText("");
                }

            }
        });


        //POPULATE DAIRY PREDICATE LAYOUT
        PredicateLayout commonDairyPredLayout = (PredicateLayout) findViewById(R.id.predicate_layout_common_dairy);

        for (int i = 0; i < commonDairyIngredients.size(); i++)
        {
            final String dairyIngredientName = commonDairyIngredients.get(i);

            //create new textview visual "tag" in the PredicateLayout
            TextView tag = new TextView(ModifyIngredientsActivity.this);
            tag.setText(dairyIngredientName);
            tag.setBackgroundColor(Color.CYAN);
            tag.setSingleLine(false);
            commonDairyPredLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


            // set onclicklistener such that if user presses the textview
            // adds ingredient to the user's current list of ingredients
            tag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    // if ingredient not in the current list, add it
                    if (!ingredientTags.containsKey(dairyIngredientName))
                        addIngredientToCurrentList(dairyIngredientName);
                    // display toast notification that adding ingredient failed
                    else
                        Toast.makeText(getApplicationContext(), "Failed to add " + dairyIngredientName + " - it has already been added!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        //POPULATE PROTEIN PREDICATE LAYOUT
        PredicateLayout commonProteinPredLayout = (PredicateLayout) findViewById(R.id.predicate_layout_common_protein);

        for (int i = 0; i < commonProteinIngredients.size(); i++)
        {
            final String proteinIngredientName = commonProteinIngredients.get(i);

            //create new textview visual "tag" in the PredicateLayout
            TextView tag = new TextView(ModifyIngredientsActivity.this);
            tag.setText(proteinIngredientName);
            tag.setBackgroundColor(Color.MAGENTA);
            tag.setTextColor(Color.WHITE);
            tag.setSingleLine(false);
            commonProteinPredLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


            // set onclicklistener such that if user presses the textview
            // adds ingredient to the user's current list of ingredients
            tag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    // if ingredient not in the current list, add it
                    if (!ingredientTags.containsKey(proteinIngredientName))
                        addIngredientToCurrentList(proteinIngredientName);
                        // display toast notification that adding ingredient failed
                    else
                        Toast.makeText(getApplicationContext(), "Failed to add " + proteinIngredientName + " - it has already been added!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        //POPULATE VEGETABLES PREDICATE LAYOUT
        PredicateLayout commonVegetablesPredLayout = (PredicateLayout) findViewById(R.id.predicate_layout_common_vegetables);

        for (int i = 0; i < commonVegetablesIngredients.size(); i++)
        {
            final String vegetablesIngredientName = commonVegetablesIngredients.get(i);

            //create new textview visual "tag" in the PredicateLayout
            TextView tag = new TextView(ModifyIngredientsActivity.this);
            tag.setText(vegetablesIngredientName);
            tag.setBackgroundColor(Color.GREEN);
            tag.setSingleLine(false);
            commonVegetablesPredLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


            // set onclicklistener such that if user presses the textview
            // adds ingredient to the user's current list of ingredients
            tag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    // if ingredient not in the current list, add it
                    if (!ingredientTags.containsKey(vegetablesIngredientName))
                        addIngredientToCurrentList(vegetablesIngredientName);
                        // display toast notification that adding ingredient failed
                    else
                        Toast.makeText(getApplicationContext(), "Failed to add " + vegetablesIngredientName + " - it has already been added!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        //POPULATE FRUITS PREDICATE LAYOUT
        PredicateLayout commonFruitsPredLayout = (PredicateLayout) findViewById(R.id.predicate_layout_common_fruits);

        for (int i = 0; i < commonFruitsIngredients.size(); i++)
        {
            final String fruitsIngredientName = commonFruitsIngredients.get(i);

            //create new textview visual "tag" in the PredicateLayout
            TextView tag = new TextView(ModifyIngredientsActivity.this);
            tag.setText(fruitsIngredientName);
            tag.setBackgroundColor(Color.RED);
            tag.setTextColor(Color.WHITE);
            tag.setSingleLine(false);
            commonFruitsPredLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


            // set onclicklistener such that if user presses the textview
            // adds ingredient to the user's current list of ingredients
            tag.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    // if ingredient not in the current list, add it
                    if (!ingredientTags.containsKey(fruitsIngredientName))
                        addIngredientToCurrentList(fruitsIngredientName);
                        // display toast notification that adding ingredient failed
                    else
                        Toast.makeText(getApplicationContext(), "Failed to add " + fruitsIngredientName + " - it has already been added!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    public void addIngredientToCurrentList(final String ingredientName)
    {
        //create new textview visual "tag" in the PredicateLayout
        TextView tag = new TextView(ModifyIngredientsActivity.this);
        tag.setText(ingredientName);
        tag.setBackgroundColor(Color.YELLOW);
        tag.setSingleLine(false);
        currentIngredPredLayout.addView(tag, new PredicateLayout.LayoutParams(3, 3));


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

        // add to map of ingredient names to corresponding textviews representing the ingredient tags
        ingredientTags.put(ingredientName, tag);

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

            if (ingredientTags.containsKey(ingredientName))
            {
                currentIngredPredLayout.removeView(ingredientTags.get(ingredientName));
                ingredientTags.remove(ingredientName);
            }

        }

    }


}
