package hr.math.android.kuharica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by mabel on 27-Feb-18.
 */

public class FAMFragment extends Fragment {
    private FloatingActionMenu floatingActionMenu;
    private com.github.clans.fab.FloatingActionButton famKategorija;
    private com.github.clans.fab.FloatingActionButton famRecept;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fam_menus_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionMenu = (FloatingActionMenu)view.findViewById(R.id.menu_fam);
        floatingActionMenu.hideMenuButton(false);

        famKategorija = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fabKategorija);
        famRecept = (com.github.clans.fab.FloatingActionButton)view.findViewById(R.id.fabRecept);

        famKategorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //za kategoriju novu
            }
        });

        famRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //za novi recept
            }
        });


        famKategorija.setLabelText("Nova kategorija");
        famRecept.setLabelText("Novi recept");
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {



            }
        });
    }
}
