package games.strategy.triplea.ui.panel.move;

import games.strategy.engine.data.events.ZoomMapListener;

public class MapData implements ZoomMapListener {

    private Integer currentZoom;
    @Override
    public void zoomMapChanged(Integer newZoom) {
        this.currentZoom = newZoom;
    }
}
