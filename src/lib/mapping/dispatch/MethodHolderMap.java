package lib.mapping.dispatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.mapping.dispatch.support.Http;

public class MethodHolderMap {

	private Map<String, MethodHolder> match = new HashMap<String, MethodHolder>();
	private Map<String, String> regexMap = new HashMap<String, String>();

	public void put(String key, MethodHolder method) {
		if (key.contains("{}")) {
			String regex = key.replace("{}", "(.*)");
			regexMap.put(regex, key);
		}
		match.put(key, method);
	}

	@Override
	public String toString() {
		StringAdd result = new StringAdd();
		match.forEach((key, value) -> {
			result.add(key + " -> " + value.getMethod().getDeclaringClass().getName() + "." + value.getMethod().getName() + "\n");
		});
		return result.toString() + "---- Map End ----\n";
	}

	private class StringAdd {
		private String string;

		private StringAdd() {
			this.string = "";
		}

		private void add(String string) {
			this.string += string;
		}

		@Override
		public String toString() {
			return string;
		}
	}

	public MethodHolder get(String key, Http http) {
		MethodHolder method = match.get(key);
		if (method != null)
			return method;
		MethodHolder holder = null;
		Pattern pattern;
		Matcher matcher;
		String regex;
		String regexValue;
		for (Entry<String, String> entry : regexMap.entrySet()) {
			regex = entry.getKey();
			regexValue = entry.getValue();
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(key);
			if (matcher.matches()) {
				method = match.get(regexValue);
				holder = method;
				if (http == null)
					return holder;
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					http.addParameter(matcher.group(i));
				}
			}

		}
		return holder;
	}
}
