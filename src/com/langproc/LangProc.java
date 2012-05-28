package com.langproc;

import java.util.List;


import org.dts.spell.SpellChecker;
import org.dts.spell.dictionary.OpenOfficeSpellDictionary;
import org.dts.spell.dictionary.SpellDictionary;
import org.dts.spell.dictionary.myspell.HEntry;
import org.dts.spell.finder.Word;
import org.dts.spell.finder.CharSequenceWordFinder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TaggedWord
{
	public String m_word;
	public String m_base_word;
	public String m_dict_tags;
	public java.util.HashSet<String> m_tags = new java.util.HashSet<String>();
	
	public TaggedWord(String word, String base_word, String dict_tags)
	{
		m_word = word;
		m_base_word = base_word;
		m_dict_tags = dict_tags;
	}
	
	public boolean hasTag(String tag)
	{
		return m_tags.contains(tag);
	}
	public void addTag(String tag)
	{
		m_tags.add(tag);
	}
	
	public String toString()
	{
		StringBuffer b = new StringBuffer(200);
		b.append(m_word);
		b.append(" ");
		b.append(m_base_word);
		b.append(" ");
		b.append(m_dict_tags);
		for(String s: m_tags)
		{
			b.append(" ");
			b.append(s);
		}
		return b.toString();
	}
}

class TagRule
{
	// pattern of processed word (.*���)
	public java.util.regex.Pattern m_word_pattern;
	// pattern of base word (.*��)
	public java.util.regex.Pattern m_base_pattern;
	// tags required for the rule
	public java.util.regex.Pattern m_dict_pattern;
	// tags required for the rule
	public java.util.List<String> m_req_tags = new java.util.LinkedList();
	// tags to add
	public java.util.List<String> m_put_tags = new java.util.LinkedList();
	
	TagRule(String word_pattern, String base_pattern, String dict_pattern,  String req_tags, String put_tags)
	{
		m_word_pattern = Pattern.compile(word_pattern, 0);
		m_base_pattern = Pattern.compile(base_pattern, 0);
		m_dict_pattern = Pattern.compile(dict_pattern, 0);
		
		CharSequenceWordFinder wf1 = new CharSequenceWordFinder(req_tags);
		while(wf1.hasNext())
		{
			Word w = wf1.next();
			m_req_tags.add(w.toString());
		}
		
		CharSequenceWordFinder wf2 = new CharSequenceWordFinder(put_tags);
		while(wf2.hasNext())
		{
			Word w = wf2.next();
			m_put_tags.add(w.toString());
		}
	}
	
	public boolean applyRule(TaggedWord w)
	{
		for(String s : m_req_tags)
		{
			if (!w.hasTag(s)) return false;
		}
		boolean has_all = true;
		for(String s : m_put_tags)
		{
			if (!w.hasTag(s)) { has_all = false; break; }
		}
		if (has_all) return false;
		
		if (m_word_pattern.matcher(w.m_word).matches()
				&& m_base_pattern.matcher(w.m_base_word).matches()
				&& m_dict_pattern.matcher(w.m_dict_tags).matches())
		{
			for(String s : m_put_tags)
			{
				w.addTag(s);
			}
			return true;
		}
		else
		{
			return false;
		}
	}
}

public class LangProc
{
	OpenOfficeSpellDictionary m_dict;
	java.util.HashSet<String> m_pronoun = new java.util.HashSet<String>();
	java.util.HashSet<String> m_prepositions = new java.util.HashSet<String>();
	java.util.HashSet<String> m_parenthesis_words = new java.util.HashSet<String>();
	java.util.HashSet<String> m_particles = new java.util.HashSet<String>();
	java.util.HashSet<String> m_conjunction = new java.util.HashSet<String>();
	
	java.util.List<TagRule> m_tag_rules = new java.util.LinkedList();
	
	static void fillSet(java.util.HashSet<String> set, String s)
	{
		CharSequenceWordFinder wf = new CharSequenceWordFinder(s);
		while(wf.hasNext())
		{
			Word w = wf.next();
			set.add(w.toString());
		}
	}
	
	void ApplyRules(TaggedWord w)
	{
		boolean applyed;
		do
		{
			applyed = false;
			for(TagRule r : m_tag_rules)
			{
				if (r.applyRule(w)) applyed = true;
			}
		} while (applyed);
	}
	
	LangProc(OpenOfficeSpellDictionary dict)
	{
	  m_dict = dict;
	  m_prepositions.add("�����");
	  m_prepositions.add("��");
	  m_prepositions.add("��");
	  m_prepositions.add("��");
	  m_prepositions.add("����");
	  m_prepositions.add("���");
	  m_prepositions.add("��");
	  m_prepositions.add("�����");
	  m_prepositions.add("����");
	  m_prepositions.add("���");
	  m_prepositions.add("�");
	  m_prepositions.add("�");
	  m_prepositions.add("��");
	  m_prepositions.add("���");
	  m_prepositions.add("��");
	  m_prepositions.add("�����");
	  m_prepositions.add("���");
	  m_prepositions.add("���");
	  m_prepositions.add("���");
	  m_prepositions.add("��");
	  m_prepositions.add("��");
	  m_prepositions.add("�");
	  m_prepositions.add("����");
	  m_prepositions.add("�����");
	  m_prepositions.add("����");
	  m_prepositions.add("����");
	  m_prepositions.add("�������");
	  m_prepositions.add("��������");
	  m_prepositions.add("����");
	  m_prepositions.add("�����");
	  m_prepositions.add("�����");
	  m_prepositions.add("��������");
	  m_prepositions.add("�� ���");
	  m_prepositions.add("� ���������");
	  m_prepositions.add("� ������");
	  m_prepositions.add("�������");
	  m_prepositions.add("���������� ��");
	  m_prepositions.add("�-��");
	  m_prepositions.add("�-���");
	  m_prepositions.add("�-����");
	  m_prepositions.add("�-��");
	  m_prepositions.add("�-����");
	  m_prepositions.add("�-�����");
	  m_prepositions.add("��-��");
	  m_prepositions.add("� ����");
	  m_prepositions.add("����� �");
	  
	  
	  m_parenthesis_words.add("�������");
	  m_parenthesis_words.add("����������");
	  m_parenthesis_words.add("��������");
	  m_parenthesis_words.add("����");
	  m_parenthesis_words.add("������");
	  m_parenthesis_words.add("��������");
	  m_parenthesis_words.add("�����");
	  m_parenthesis_words.add("�����");
	  m_parenthesis_words.add("�����");
	  m_parenthesis_words.add("�������");
	  m_parenthesis_words.add("�����");
	  m_parenthesis_words.add("���������");
	  m_parenthesis_words.add("��������");
	  m_parenthesis_words.add("��-�����");
	  m_parenthesis_words.add("��-�����");
	  m_parenthesis_words.add("���");
	  m_parenthesis_words.add("�� ����");
	  m_parenthesis_words.add("������");
	  m_parenthesis_words.add("�� �����");
	  m_parenthesis_words.add("�� ����");
	  m_parenthesis_words.add("�� �������");
	  m_parenthesis_words.add("����� ��");
	  m_parenthesis_words.add("������");
	  m_parenthesis_words.add("������ �������");
	  m_parenthesis_words.add("����� �������");
	  m_parenthesis_words.add("��� �� ������");
	  m_parenthesis_words.add("�� ������");
	  
	  fillSet(m_pronoun, "� �� �� ���� ���� �� �� ����");
	  
	  fillSet(m_particles, "���, �����, ��, ��, ���, ��, ���");
	  fillSet(m_particles, "�����, �����, ������, �����, ������, �����, ����");
	  fillSet(m_particles, "��, �, ��");
	  fillSet(m_particles, "�����, ����, ���, ��� ��, ��������");
	  fillSet(m_particles, "�, �, ��, ����, ��, �����, ���, �, ��, ��");
	  fillSet(m_particles, "���, �����, �����, �����");
	  fillSet(m_particles, "��, �, ��");
	  fillSet(m_particles, "���, ����, ���, �����, ����, ������");
	  fillSet(m_particles, "��, �����, ����, �� ��, �� ��");
	  fillSet(m_particles, "���, �����, �����, ����, ������, �������, ���, �����");
	  fillSet(m_particles, "��, �� �� ����");
	  
	  fillSet(m_conjunction, "�, ���, �, ��" );
	  
	  m_tag_rules.add(new TagRule(".*���", ".*��", ".*", "�����", "���, ���"));
	  m_tag_rules.add(new TagRule(".*��", ".*��",  ".*", "�����", "���, ��"));
	  m_tag_rules.add(new TagRule(".*", ".*",  ".*[aioe].*", "", "����"));
	  
	  m_tag_rules.add(new TagRule(".*", ".*��",  ".*", "", "ĳ���"));
	  m_tag_rules.add(new TagRule(".*", ".*��",  ".*", "", "�����"));
	  m_tag_rules.add(new TagRule(".*", ".*��|.*��",  ".*", "", "�����"));
	  m_tag_rules.add(new TagRule(".*", ".*��",  ".*", "", "ĳ������"));
	}
		
	
	Matcher createMatcher(CharSequence text, String regexp, int flags)
	{
	   return Pattern.compile(regexp, flags).matcher(text) ;
	}
	
	private void printForms(String txt)
	{
	      List<HEntry> list = m_dict.checkList(txt.toLowerCase());
	      for(HEntry s:list)
	      {
	    	  TaggedWord w = new TaggedWord(txt.toLowerCase(), s.word, s.astr);
	    	  if (txt.equals(txt.toUpperCase())) w.addTag("Cap");
	    	  if (Character.isUpperCase(txt.charAt(0))) w.addTag("StartCap");
	    	  if (txt.equals(txt.toLowerCase())) w.addTag("Low");
	    	  if (txt.toLowerCase().equals(s.word)) w.addTag("Base");
	    	  
	    	  //System.out.print( "   " + s.word + " " + s.astr + " " );

	    	  if (m_prepositions.contains(s.word)) w.addTag("�����");
	    	  if (m_parenthesis_words.contains(s.word)) w.addTag("�����");
	    	  if (m_particles.contains(s.word)) w.addTag("����");
	    	  if (m_pronoun.contains(s.word)) w.addTag("����");
	    	  if (m_conjunction.contains(s.word)) w.addTag("����");
	    	  
	    	  ApplyRules(w);
	    	  
	    	  System.out.println(" " + w);
	      }
	}

	private void checkGrammar(String txt)
	{
		CharSequenceWordFinder wf = new CharSequenceWordFinder(txt);
		while(wf.hasNext())
		{
			Word w = wf.next();
			System.out.println(w.toString());
			printForms(w.toString());
			
		}
	}
	
  private void test(SpellChecker checker, String txt)
  {
    Word badWord = checker.checkSpell(txt) ;

    if (badWord == null)
      System.out.println("All OK!!!");
    else
    {
      System.out.println("Bad words: " + badWord);
      List list = checker.getDictionary().getSuggestions(badWord);
      List<String> wl = list;
      for (String s : wl)
      {
    	  System.out.println(s);
      }
    }
  }
  


  public static void main(String[] args)
  {
	try
	{
	  (new LangProc(new OpenOfficeSpellDictionary("uk_UA")))
	  	.checkGrammar("����������� �� ����� ����������� ���������� ��������, ���� ������� ������� �� ����������. �������� �� ��������, ���������, ����������, ���� ������� �� ������ ����������� ����� �������." + 
"������������ ���������� �������� ������� ����, ��� ����� � ���������� ����������� �������� (��� ����������) ������� ��� ��������� �������� ������ �� ������� � ������. �������� �����������:");
	  
//	  (new LangProc(new OpenOfficeSpellDictionary("uk_UA")))
//	  	.checkGrammar("ѳ� ������ ���� ��������� ҳ�� ��� �� ����");
    }
    catch (Exception e)
    {
      e.printStackTrace() ;
    }
  }
}