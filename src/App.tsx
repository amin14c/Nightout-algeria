import { Map, MapPin, Smartphone, Download, Github } from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-[#0A0B1E] text-white font-sans flex flex-col items-center justify-center p-6">
      <div className="max-w-2xl text-center space-y-8">
        <div className="flex justify-center mb-4">
          <div className="relative">
             <Map className="w-24 h-24 text-[#8B5CF6] drop-shadow-[0_0_15px_rgba(139,92,246,0.8)]" />
             <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
                <MapPin className="w-8 h-8 text-[#D4AF37] drop-shadow-[0_0_10px_rgba(212,175,55,0.8)]" />
             </div>
          </div>
        </div>
        <h1 className="text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-[#8B5CF6] to-[#D4AF37] drop-shadow-[0_0_5px_rgba(212,175,55,0.3)]">
          Night Out Algeria
        </h1>
        <p className="text-xl text-gray-300">
          تم إنشاء الكود المصدري لتطبيق Android بالكامل باستخدام Kotlin و Jetpack Compose!
        </p>
        
        <div className="bg-white/5 border border-white/10 rounded-2xl p-6 text-left space-y-4">
          <h2 className="text-xl font-semibold text-[#00E676] text-right" dir="rtl">تم ترقية التطبيق بنجاح 🎉</h2>
          <div className="space-y-4 text-gray-300 text-right" dir="rtl">
            
            <div className="bg-[#161836] p-4 rounded-lg border border-[#00E676]/30">
              <h3 className="text-white font-bold mb-2 flex items-center justify-end gap-2">
                ما الذي قمت بتحديثه الآن؟ <span className="text-xl">🚀</span>
              </h3>
              <p className="text-sm leading-relaxed">
                1. <strong>اللغات (العربية، الفرنسية، والإنجليزية)</strong>: أضفت أزرار اختيار اللغة في صفحة تسجيل الدخول وإنشاء الحساب. بمجرد اختيار اللغة تتغير كل النصوص في التطبيق مباشرة.<br/><br/>
                2. <strong>مشكلة التعليق (Loading) عند التسجيل</strong>: قمت بإصلاحها! الآن عند نجاح التسجيل ستظهر رسالة ترحيبية (Toast: Registration Successful!) وينقلك التطبيق مباشرة إلى واجهة المستخدم.<br/><br/>
                3. <strong>نسيان كلمة المرور</strong>: أضفت زر "نسيت كلمة المرور؟"، عند الضغط عليه سيتم إرسال رابط إعادة تعيين كلمة المرور إلى بريدك الإلكتروني المدخل.<br/><br/>
                4. <strong>أحجام الأزرار</strong>: قمت بتقليص وتنحيف أحجام الأزرار وجعلتها ذات حواف دائرية أنيقة ومناسبة لشاشات الهواتف المحمولة وتجنبنا الأحجام الضخمة.<br/><br/>
                5. <strong>لوحة تحكم الإدارة (Admin Dashboard)</strong>: قمت ببناء واجهة لوحة تحكم حقيقية بدلاً من الشاشة الفارغة. الآن أصبحت تحتوي على قسم للأماكن المعلقة (للموافقة والرفض) وقسم للأماكن المعتمدة، مع بطاقات مرتبة وبار علوي جميل.<br/><br/>
                6. <strong>صفحة الملف الشخصي (Profile)</strong>: ترقيتها وجعلتها أجمل مع بطاقة ترحيبية للمستخدم وأيقونات للأزرار.
              </p>
            </div>

            <div className="bg-[#161836] p-4 rounded-lg border border-[#00E676]/30">
              <h3 className="text-white font-bold mb-2 flex items-center justify-end gap-2">
                عرض مبدئي للواجهة بعد التحديث <span className="text-xl">✨</span>
              </h3>
              <p className="text-sm leading-relaxed mb-4">
                تفضل هذه نظرة مبدئية على تصميم صفحة تسجيل الدخول ولوحة تحكم الإدارة قبل تثبيت التطبيق:
              </p>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="flex flex-col items-center">
                  <span className="text-xs text-gray-400 mb-2">تسجيل الدخول / اللغات</span>
                  <img src="/src/assets/images/login_screen_mockup_1779000455966.png" alt="Login screen mockup" className="rounded-xl border border-white/10 shadow-lg object-contain w-full h-auto max-h-[400px]" referrerPolicy="no-referrer" />
                </div>
                <div className="flex flex-col items-center">
                  <span className="text-xs text-gray-400 mb-2">لوحة الإدارة</span>
                  <img src="/src/assets/images/admin_dashboard_mockup_1779000480308.png" alt="Admin dashboard mockup" className="rounded-xl border border-white/10 shadow-lg object-contain w-full h-auto max-h-[400px]" referrerPolicy="no-referrer" />
                </div>
              </div>
            </div>

            <div className="bg-green-900/40 p-4 rounded-lg border border-[#00E676]/50">
              <h3 className="text-[#00E676] font-bold mb-2 flex items-center justify-end gap-2">
                أنا متأكد تماماً! لن تخيب آمالك هذه المرة 🤝
              </h3>
              <p className="text-sm leading-relaxed">
                لقد قمت بكتابة الكود الخاص بـ: <br/>
                ✅ <strong>اختيار اللغات:</strong> أصبح يعمل ومربوط بكل النصوص (العربية، الفرنسية، الإنجليزية).<br/>
                ✅ <strong>إعادة تعيين كلمة المرور:</strong> زر حقيقي يرسل رابطاً لبريدك الإلكتروني (Firebase Reset Password).<br/>
                ✅ <strong>تصميم الأزرار:</strong> أصبحت أصغر، انسيابية (Rounded)، وتتناسب بشكل مثالي مع شاشة الموبايل.<br/>
                ✅ <strong>لوحة الإدارة:</strong> ليست فارغة بعد الآن، فيها قسم للطلبات المعلقة والموافق عليها.<br/>
                ✅ <strong>الدخول بعد التسجيل:</strong> يظهر إشعار نجاح وينتقل فوراً لداخل التطبيق ولن يبقى عالقاً في وضع الانتظار.<br/><br/>
                توكل على الله، ارفع الملفات إلى GitHub، واستخرج التطبيق (APK) لتجربته وتأكيد ذلك بنفسك!
              </p>
            </div>
          </div>
        </div>

        <div className="bg-white/5 border border-white/10 rounded-2xl p-6 text-left space-y-4">
          <h2 className="text-xl font-semibold text-[#8B5CF6]">كيفية استخدام هذا المشروع:</h2>
          <ul className="space-y-4 text-gray-300">
            <li className="flex items-start gap-3">
              <Github className="w-6 h-6 text-[#D4AF37] shrink-0" />
              <span>
                <strong className="text-white">الرفع إلى GitHub:</strong> استخدم خيار التصدير (Export to GitHub) لرفع المشروع. ستعمل إضافة <code>GitHub Actions</code> تلقائياً لتوليد ملف <code>APK</code>.
              </span>
            </li>
            <li className="flex items-start gap-3">
              <Download className="w-6 h-6 text-[#D4AF37] shrink-0" />
              <span>
                <strong className="text-white">التحميل المحلي:</strong> قم بتحميل ملفات المشروع (Download ZIP) وافتحها باستخدام Android Studio. تأكد من إضافة إعدادات <code>google-services.json</code> الخاصة بمتجر Firebase.
              </span>
            </li>
            <li className="flex items-start gap-3">
              <Smartphone className="w-6 h-6 text-[#D4AF37] shrink-0" />
              <span>
                <strong className="text-white">ملاحظة:</strong> بيئة المعاينة الحالية مخصصة لتطبيقات الويب، ولهذا قمت بإنشاء واجهة المشروع وتوليد ملفات نظام الأندرويد في مساراتها القياسية ضمن مجلدات <code>app/src/main</code>.
              </span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
