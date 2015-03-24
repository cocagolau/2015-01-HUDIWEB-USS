package lib.mapping.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.mapping.dispatch.support.Http;

public class UriMap {

	private Map<String, List<String>> match = new HashMap<String, List<String>>();
	private List<String> regexList = new ArrayList<String>();

	public void put(String key, List<String> methodArray) {
		if (key.contains("{}")) {
			String regex = key.replace("{}", "(.*)");
			regexList.add(regex);
			match.put(regex, methodArray);
			return;
		}
		match.put(key, methodArray);
	}

	public List<String> get(String key, Http http) {
		List<String> methodArray = match.get(key);
		if (methodArray != null)
			return methodArray;
		for (int i = 0; i < regexList.size(); i++) {
			Pattern pattern = Pattern.compile(regexList.get(i));
			Matcher matcher = pattern.matcher(key);
			if (matcher.matches()) {
				methodArray = match.get(regexList.get(i));
				for (int j = 1; j < matcher.groupCount() + 1; j++) {
					http.addParameter(matcher.group(i));
				}
			}
		}
		return methodArray;
	}
}
