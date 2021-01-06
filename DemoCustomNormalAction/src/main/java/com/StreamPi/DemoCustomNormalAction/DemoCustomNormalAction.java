package com.StreamPi.DemoCustomNormalAction;

import com.StreamPi.ActionAPI.ActionProperty.Property.ControlType;
import com.StreamPi.ActionAPI.ActionProperty.Property.Property;
import com.StreamPi.ActionAPI.ActionProperty.Property.Type;
import com.StreamPi.ActionAPI.NormalAction.NormalAction;
import com.StreamPi.Util.Version.Version;
import java.util.ArrayList;

public class DemoCustomNormalAction extends NormalAction
{

    public DemoCustomNormalAction()
    {
        setName("Demo Action");
        setAuthor("dubbadhar");
        setRepo("https://github.com/Stream-Pi/DemoCustomNormalAction");
        setVersion(new Version(1,0,0));

        setCategory("Custom Actions");

    }

    @Override
    public void initProperties() throws Exception {
        //Called First

        Property privateProperty = new Property("InvisibleProperty", Type.STRING);
        privateProperty.setVisible(false);

        Property sliderPropertyInt = new Property("SliderPropertyInt", Type.INTEGER);
        sliderPropertyInt.setControlType(ControlType.SLIDER_INTEGER);
        sliderPropertyInt.setMaxIntValue(5);

        Property sliderPropertyDouble= new Property("SliderPropertyDouble", Type.DOUBLE);
        sliderPropertyInt.setControlType(ControlType.SLIDER_DOUBLE);

        Property comboBoxProperty = new Property("ComboBoxProperty", Type.LIST);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Choice 1");
        arrayList.add("Choice 2");

        comboBoxProperty.setListValue(arrayList);

        Property property = new Property("ServerProperty2", Type.STRING);
        property.setDisplayName("ServerProperty2 [Required]");
        property.setCanBeBlank(false);

        Property property1 = new Property("ServerProperty1", Type.STRING);
        property1.setDefaultValueStr("23");

        Property booleanProperty =  new Property("ServerBooleanProperty1", Type.BOOLEAN);
        booleanProperty.setDefaultValueBoolean(true);

        addServerProperties(
                property1,
                property,
                booleanProperty,
                privateProperty,
                comboBoxProperty,
                sliderPropertyDouble,
                sliderPropertyInt
        );

        Property clientProperty1 = new Property("ClientProperty1", Type.STRING);
        clientProperty1.setCanBeBlank(false);

        Property clientProperty2 = new Property("ClientProperty2", Type.STRING);
        clientProperty2.setDefaultValueStr("Default Prop");

        addClientProperties(
                clientProperty1,
                clientProperty2,
                new Property("ClientBooleanProperty1", Type.BOOLEAN),
                privateProperty,
                comboBoxProperty,
                sliderPropertyDouble,
                sliderPropertyInt
        );
    }


    @Override
    public void initAction()  {
        // This is called after initProperties()
    }

    @Override
    public void onActionClicked()
    {
        //Called when action is clicked

        System.out.println("Action Called!");
    }

    @Override
    public void onShutDown() throws Exception {
        // TODO Auto-generated method stub

    }
}
