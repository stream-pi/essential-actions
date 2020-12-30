module com.StreamPi.OBSSuite.Mother
{
    requires com.StreamPi.ActionAPI;
    requires com.StreamPi.Util;

    
    provides com.StreamPi.ActionAPI.NormalAction.NormalAction with com.StreamPi.OBSSuite.Mother.Mother;
}