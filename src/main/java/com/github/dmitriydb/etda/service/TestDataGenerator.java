package com.github.dmitriydb.etda.service;

import com.github.dmitriydb.etda.model.simplemodel.domain.Department;
import com.github.dmitriydb.etda.model.simplemodel.domain.Employee;
import com.github.dmitriydb.etda.model.simplemodel.domain.Title;
import com.github.dmitriydb.etda.model.simplemodel.domain.TitleOrder;
import com.github.dmitriydb.etda.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

@Service
public class TestDataGenerator {

    private static List<String> availableNames = new ArrayList<>();
    private static List<String> availableSurnames = new ArrayList<>();
    private static Set<String> occipiedDepartmentNames = new HashSet<>();
    private static Set<String> occipiedDepartmentCodes = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(TestDataGenerator.class);

    static {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("russian_names.csv");
        InputStream is2 = classloader.getResourceAsStream("russian_surnames.csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        BufferedReader in2 = new BufferedReader(new InputStreamReader(is2));
        Pattern pattern = Pattern.compile("^[а-яА-Яa-zA-Z'\\s-]{1,30}$");

        try {
            in.readLine();
            String line;
            while ((line = in.readLine()) != null){
                String[] splits = line.split(";");
                String name = splits[1];
                if (pattern.matcher(name).matches())
                    availableNames.add(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in2.readLine();
            String line;
            while ((line = in2.readLine()) != null){
                String[] splits = line.split(";");
                String surname = splits[1];
                if (pattern.matcher(surname).matches())
                    availableSurnames.add(surname);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee generateEmployee(){
        Employee e = new Employee();
        String surname = availableSurnames.get(ThreadLocalRandom.current().nextInt(availableSurnames.size()));
        boolean isMale = true;
        if (surname.endsWith("а") || surname.endsWith("я"))
            isMale = false;
        String name;
        while (true) {
            name = availableNames.get(ThreadLocalRandom.current().nextInt(availableNames.size()));
            if (isMale && (name.endsWith("а") || name.endsWith("я")))
                continue;
            break;
        }
        e.setFirstName(name);
        e.setLastName(surname);

        if (isMale)
            e.setGender('М');
        else
            e.setGender('Ж');

        e.setHireDate(generateToDate());
        e.setBirth_date(generateDate());
        return e;
    }

    public static Department generateDepartment(){
        Department d = new Department();
        String[] prefix = new String[]{"Super", "Mega", "Hyper", "Awesome", "Elite", "Big"};
        String[] suffix = new String[]{"PR", "Marketing", "Soft development", "Supply", "Helpdesk", "Q&A", "Testing", "R&D"};
        String dcode;
        do{
            String s = String.valueOf(ThreadLocalRandom.current().nextInt(1000));
            while (s.length() < 3) s = "0" + s;
            dcode = "d" + s;
        }
        while (occipiedDepartmentCodes.contains(dcode));
        String dname;
        do{
            dname = prefix[ThreadLocalRandom.current().nextInt(prefix.length)] + " " + suffix[ThreadLocalRandom.current().nextInt(suffix.length)];
        }
        while (occipiedDepartmentNames.contains(dname));

        occipiedDepartmentCodes.add(dcode);
        occipiedDepartmentNames.add(dname);
        d.setDepartmentId(dcode);
        d.setName(dname);
        return d;
    }

    public static Title generateTitle(){
        String[] prefix = new String[]{"Super", "Mega", "Hyper", "Awesome", "Elite", "Big", "Tech", "Main", "Junior"};
        String[] suffix = new String[]{"Manager", "Leader", "Engineer", "Staff", "Worker", "Helper"};
        String title = prefix[ThreadLocalRandom.current().nextInt(prefix.length)] + " " + suffix[ThreadLocalRandom.current().nextInt(suffix.length)];
        Title t = new Title();
        TitleOrder to = new TitleOrder();
        to.setTitle(title);
        to.setFromDate(generateDate());
        t.setToDate(generateToDate());
        t.setTitleOrder(to);
        return t;
    }


    public static Date generateDate(){
        int year = ThreadLocalRandom.current().nextInt(1900, 2000);
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int day = ThreadLocalRandom.current().nextInt(1, 29);
        String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
        return Date.valueOf(date);
    }

    public static Date generateToDate() {
        int year = ThreadLocalRandom.current().nextInt(2001, 2020);
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int day = ThreadLocalRandom.current().nextInt(1, 29);
        String date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
        return Date.valueOf(date);
    }
}
