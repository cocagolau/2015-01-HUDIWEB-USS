package lib.mapping.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.mapping.http.Http;

public class UriMap {

	private Map<String, List<MethodHolder>> match = new HashMap<String, List<MethodHolder>>();
	private List<String> regexList = new ArrayList<String>();

	public void put(String key, List<MethodHolder> methodList) {
		if (key.contains("{}")) {
			String regex = key.replace("{}", "(.*)");
			regexList.add(regex);
			match.put(regex, methodList);
			return;
		}
		match.put(key, methodList);
	}

	public List<MethodHolder> get(String key, Http http) {
		List<MethodHolder> methodArray = match.get(key);
		if (methodArray != null)
			return methodArray;
		for (int i = 0; i < regexList.size(); i++) {
			Pattern pattern = Pattern.compile(regexList.get(i));
			Matcher matcher = pattern.matcher(key);
			if (matcher.matches()) {
				methodArray = match.get(regexList.get(i));
				for (int j = 1; j < matcher.groupCount() + 1; j++) {
					http.addUriVariable(matcher.group(i));
				}
			}
		}
		return methodArray;
	}
}
