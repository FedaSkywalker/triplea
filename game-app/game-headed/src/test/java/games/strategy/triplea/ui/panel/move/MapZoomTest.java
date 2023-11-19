package games.strategy.triplea.ui.panel.move;

import games.strategy.triplea.ui.BottomBar;
import games.strategy.triplea.ui.panels.map.MapPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MapZoomTest {

 @InjectMocks
 private MapPanel mapPanel = new MapPanel();

 @Mock private BottomBar bottomBar;

 @Mock private MapData mapData;


 @Test
 public void testAddOneZoomListener(){
  MapPanel mockedPanel = mock(MapPanel.class);
  mockedPanel.addZoomMapListener(bottomBar);
  verify(mockedPanel,
          times(1))
          .addZoomMapListener(bottomBar);
 }

 @Test
 public void testAddMultipleZoomListeners(){
  MapPanel mockedPanel = mock(MapPanel.class);
  mockedPanel.addZoomMapListener(bottomBar);
  mockedPanel.addZoomMapListener(mapData);

  verify(mockedPanel,
          times(1))
          .addZoomMapListener(bottomBar);
  verify(mockedPanel,
          times(1))
          .addZoomMapListener(mapData);
 }

 @Test
 public void tesRemoveZoomListenersAfterNotify(){
  MapPanel mockedPanel = mock(MapPanel.class);
  mockedPanel.addZoomMapListener(bottomBar);
  mockedPanel.addZoomMapListener(mapData);
  mockedPanel.removeZoomMapListener(bottomBar);
  mockedPanel.removeZoomMapListener(mapData);

  verify(mockedPanel,
          times(1))
          .removeZoomMapListener(bottomBar);
  verify(mockedPanel,
          times(1))
          .removeZoomMapListener(mapData);

 }


 @Test
 public void notifyZoomListener(){
  mapPanel.addZoomMapListener(mapData);
  mapPanel.addZoomMapListener(bottomBar);
  mapPanel.setScale(20.5);
  int percentage = scaleToPercentage(20.5);
  verify(mapData).zoomMapChanged(percentage);
  mapPanel.removeZoomMapListener(mapData);
  mapPanel.removeZoomMapListener(bottomBar);
 }

 @Test
 public void notifyZoomListenerAfterRemove(){
  mapPanel.addZoomMapListener(bottomBar);
  mapPanel.setScale(20.5);
  int percentage = scaleToPercentage(20.5);
  verify(bottomBar).zoomMapChanged(percentage);
  mapPanel.removeZoomMapListener(bottomBar);
  mapPanel.setScale(50.5);
  verify(bottomBar).zoomMapChanged(200);
 }

 private int scaleToPercentage(double scale) {
  return ((int) scale) * 10;
 }

}
