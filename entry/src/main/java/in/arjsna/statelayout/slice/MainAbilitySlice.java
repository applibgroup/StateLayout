package in.arjsna.statelayout.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import com.lufficc.statelayout.FadeScaleViewAnimProvider;
import com.lufficc.statelayout.FadeViewAnimProvider;
import com.lufficc.statelayout.StateLayout;
import in.arjsna.statelayout.ResourceTable;

/**
 * MainAbilitySlice.
 */
public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        StateLayout stateLayout = (StateLayout) findComponentById(ResourceTable.Id_stateLayout);
        stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());

        findComponentById(ResourceTable.Id_error).setClickedListener(c ->
                stateLayout.showErrorView()
        );

        findComponentById(ResourceTable.Id_empty).setClickedListener(c ->
                stateLayout.showEmptyView()
        );

        findComponentById(ResourceTable.Id_progress).setClickedListener(c ->
                stateLayout.showProgressView()
        );

        findComponentById(ResourceTable.Id_content).setClickedListener(c ->
                stateLayout.showContentView()
        );


        Text fadeText = (Text) findComponentById(ResourceTable.Id_fade);
        Text fadeScaleText = (Text) findComponentById(ResourceTable.Id_fade_scale);

        fadeText.setText("✔ " + getString(ResourceTable.String_btn_fade));

        fadeText.setClickedListener(c -> {
            stateLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());
            fadeScaleText.setText(getString(ResourceTable.String_btn_fade_scale));
            fadeText.setText("✔ " + getString(ResourceTable.String_btn_fade));
        });

        fadeScaleText.setClickedListener(c -> {
            stateLayout.setViewSwitchAnimProvider(new FadeScaleViewAnimProvider());
            fadeText.setText(getString(ResourceTable.String_btn_fade));
            fadeScaleText.setText("✔ " + getString(ResourceTable.String_btn_fade_scale));
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
