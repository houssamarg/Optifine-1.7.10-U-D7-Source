package optifine;

import java.lang.reflect.Field;

public class FieldLocatorName implements IFieldLocator
{
    private ReflectorClass reflectorClass = null;
    private String targetFieldName = null;

    public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName)
    {
        this.reflectorClass = reflectorClass;
        this.targetFieldName = targetFieldName;
    }

    public Field getField()
    {
        Class cls = this.reflectorClass.getTargetClass();

        if (cls == null)
        {
            return null;
        }
        else
        {
            try
            {
                Field e = cls.getDeclaredField(this.targetFieldName);
                e.setAccessible(true);
                return e;
            }
            catch (NoSuchFieldException var3)
            {
                Config.log("(Reflector) Field not present: " + cls.getName() + "." + this.targetFieldName);
                return null;
            }
            catch (SecurityException var4)
            {
                var4.printStackTrace();
                return null;
            }
            catch (Throwable var5)
            {
                var5.printStackTrace();
                return null;
            }
        }
    }
}
