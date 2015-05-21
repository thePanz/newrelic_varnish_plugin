package de.bauerexcel.newrelic.plugins.varnish;

import org.w3c.dom.Element;

/**
 * Created by jan.schumann on 20.05.15.
 */
public class Metric {

    private final Element element;

    public Metric(Element statsElement) {
        this.element = statsElement;
    }

    public String getName() {
        return getTagValue("name");
    }

    public String getType() {
        String type = getTagValue("type");

        return null == type ? "main" : type;
    }

    public Float getValue() {
        String value = getTagValue("value");

        return null == value ? 0.0f : Float.parseFloat(value);
    }

    /**
     * Determine if this flag is a counter.
     *
     * Possible values for the plag property are
     *
     * - 'a' - Accumulator (deprecated, use 'c')
     * - 'b' - Bitmap
     * - 'c' - Counter, never decreases.
     * - 'g' - Gauge, goes up and down
     * - 'i' - Integer (deprecated, use 'g')
     *
     * @todo the a flag check can be removed as soon as the next varnish release arrives
     *
     * @return Boolean
     */
    public Boolean isCounter() {
        String flag = getTagValue("flag");

        return null != flag && (flag.equals("a") || flag.equals("c"));
    }

    /**
     * Determine if this flag is a gauge, which means that the provided value
     * should be treated as a rate. (unit/second)
     *
     * @return Boolean
     */
    public Boolean isGauge() {
        String flag = getTagValue("flag");

        return null != flag && flag.equals("g");
    }

    /**
     * Determine if this flag is a bitmap.
     * e.g. PER BACKEND COUNTERS/happy
     *
     * @return Boolean
     */
    public Boolean isBitmap() {
        String flag = getTagValue("flag");

        return null != flag && flag.equals("b");
    }

    public String getLabel() {
        return getTagValue("description");
    }

    public String getIdent() {
        return getTagValue("ident");
    }

    private String getTagValue(String tagName) {
        return this.element.getElementsByTagName(tagName).item(0) != null ? this.element.getElementsByTagName(tagName).item(0).getTextContent().toLowerCase() : null;
    }

    public Boolean hasIdent() {
        return getTagValue("ident") != null;
    }
}
