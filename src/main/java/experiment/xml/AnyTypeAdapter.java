package experiment.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AnyTypeAdapter extends XmlAdapter<Object, Object>
{
	public Object unmarshal(Object v)
	{
		return v;
	}


	public Object marshal(Object v)
	{
		return v;
	}
}
