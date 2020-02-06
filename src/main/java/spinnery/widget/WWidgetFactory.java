package spinnery.widget;

import spinnery.Spinnery;
import spinnery.widget.api.WModifiableCollection;
import spinnery.widget.api.WPosition;
import spinnery.widget.api.WSize;

public class WWidgetFactory {
    protected WModifiableCollection parent;

    public WWidgetFactory(WModifiableCollection parent) {
        this.parent = parent;
    }

    public <W extends WAbstractWidget> W build(Class<W> tClass, WPosition position, WSize size) {
        try {
            W widget = tClass.newInstance();

            if (position != null) widget.setPosition(position);
            if (size != null) widget.setSize(size);
            if (parent instanceof WAbstractWidget) {
                widget.setInterface(((WAbstractWidget) parent).getInterface());
            } else if (parent instanceof WInterface) {
                widget.setInterface((WInterface) parent);
            }

            return widget;
        } catch (IllegalAccessException | InstantiationException e) {
            Spinnery.LOGGER.error("Could not build {}", tClass.getSimpleName(), e);
            return null;
        }
    }
}