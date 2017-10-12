package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicUserId 
{
Pattern pattern;
Matcher matcher;
private static final String UserIdPattern="[a-zA-Z]";

public DynamicUserId()
{
  pattern=Pattern.compile(UserIdPattern);
}
/*
* validate userId with pattern
*/
public boolean validate (String userId)
{
  matcher=pattern.matcher(userId);
  return matcher.matches();
}
}