package in.arjsna.statelayout;

import com.lufficc.statelayout.FadeScaleViewAnimProvider;
import com.lufficc.statelayout.FadeViewAnimProvider;
import com.lufficc.statelayout.StateLayout;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Component;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class StateLayoutTest {
    StateLayout stateLayout;

    @Before
    public void setUp() {
        Context context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        Component component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_ability_main, null, false);
        stateLayout = (StateLayout) component.findComponentById(ResourceTable.Id_stateLayout);
    }

    @Test
    public void testShowEmptyView() {
        int before = stateLayout.findComponentById(ResourceTable.Id_empty_content).getVisibility();
        assertEquals(before, Component.HIDE);

        stateLayout.showEmptyView();

        int after = stateLayout.findComponentById(ResourceTable.Id_empty_content).getVisibility();
        assertEquals(after, Component.VISIBLE);
    }

    @Test
    public void testShowProgressView() {
        int before = stateLayout.findComponentById(ResourceTable.Id_progress_content).getVisibility();
        assertEquals(before, Component.HIDE);

        stateLayout.showProgressView();

        int after = stateLayout.findComponentById(ResourceTable.Id_progress_content).getVisibility();
        assertEquals(after, Component.VISIBLE);
    }

    @Test
    public void testShowErrorView() {
        int before = stateLayout.findComponentById(ResourceTable.Id_error_content).getVisibility();
        assertEquals(before, Component.HIDE);

        stateLayout.showErrorView();

        int after = stateLayout.findComponentById(ResourceTable.Id_error_content).getVisibility();
        assertEquals(after, Component.VISIBLE);
    }

    @Test
    public void testShowContentView() {
        int before = stateLayout.findComponentById(ResourceTable.Id_main_content).getVisibility();
        assertEquals(before, Component.VISIBLE);

        stateLayout.showErrorView();

        int after = stateLayout.findComponentById(ResourceTable.Id_main_content).getVisibility();
        assertEquals(after, Component.HIDE);

        stateLayout.showContentView();

        int after2 = stateLayout.findComponentById(ResourceTable.Id_main_content).getVisibility();
        assertEquals(after2, Component.VISIBLE);


    }

    @Test
    public void testFadeViewAnimProvider() {
        FadeViewAnimProvider animProvider = new FadeViewAnimProvider();
        stateLayout.setViewSwitchAnimProvider(animProvider);
        assertTrue(stateLayout.getShowAnimation() instanceof AnimatorProperty);
        assertTrue(stateLayout.getHideAnimation() instanceof AnimatorProperty);
    }

    @Test
    public void testFadeScaleViewAnimProvider() {
        FadeScaleViewAnimProvider animProvider = new FadeScaleViewAnimProvider();
        stateLayout.setViewSwitchAnimProvider(animProvider);
        assertTrue(stateLayout.getShowAnimation() instanceof AnimatorProperty);
        assertTrue(stateLayout.getHideAnimation() instanceof AnimatorProperty);
    }
}
