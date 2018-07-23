package ru.production.ssobolevsky.foodgram.presentation.delete;

/**
 * Created by pro on 18.07.2018.
 */

public class DELETE {

    /**
     *
     StorageReference reference = FirebaseStorage.getInstance().getReference();
     Log.wtf("TAGF", "Перед");
     reference.child("recipes").putFile(Uri.fromFile(new File("https://img08.rl0.ru/eda/c620x415i/s2.eda.ru/StaticContent/Photos/120705181646/120721221514/p_O.jpg")))
     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
    Log.wtf("TAGF", "В");
    DatabaseReference dataRef = MyFirebaseData.getFirebaseDatabaseReference()
    .child(MyFirebaseData.POSTS_TABLE)
    .child(MyFirebaseData.SOUPS_TABLE)
    .push();
    dataRef.setValue(new RecipeEntity(dataRef.toString(),"Сырный суп по‑французски с курицей", "1. В кастрюлю на 3 литра положить мясо и налить воды. Как только бульон начнет кипеть, добавить 1 чайную ложку соли, пару горошков душистого перца и черного, 2–3 листика лаврового листа. Варить от момента закипания 20 минут. Затем мясо вынуть.\n" +
    "\n" +
    "2. Картофель почистить и нарезать кубиками. Лук нарезать кубиками. Морковь натереть на терке. Мясо порезать небольшими кусочками. Плавленый сыр (если в виде брусочка) натереть на терке или порезать кубиками.\n" +
    "\n" +
    "3. В кипящий бульон добавить картофель. С момента закипания 5–7 минут.\n" +
    "\n" +
    "4. В это время сделать слабую зажарку на сливочном масле. Сначала положить лук, затем морковь. Слегка посолить и поперчить. Готовую зажарку добавить в суп и варить еще 5–7 минут.\n" +
    "\n" +
    "5. Затем добавить порезанное мясо. Варить 3–4 минуты, добавить плавленый сыр, хорошенько помешать и выключить огонь.\n" +
    "\n" +
    "6. Перед подачей посыпать зеленью. По желанию подавать с гренками.", "Куриное филе 500 г\n" +
    "Плавленый сыр 200 г\n" +
    "Картофель 400 г\n" +
    "Лук 150 г\n" +
    "Морковь 180 г\n" +
    "Сливочное масло по вкусу\n" +
    "Соль по вкусу\n" +
    "Перец черный молотый по вкусу\n" +
    "Зелень по вкусу\n" +
    "Лавровый лист 3 штуки\n" +
    "Гренки по вкусу\n" +
    "Черный перец горошком 2 штуки", taskSnapshot.getDownloadUrl()));
    }

    })
     .addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
    Log.wtf("TAGF", "FAIL");
    }
    });

     */
}
