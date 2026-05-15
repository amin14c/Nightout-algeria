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
          <h2 className="text-xl font-semibold text-[#8B5CF6] text-right" dir="rtl">كيفية إعداد المشروع (مهم جداً):</h2>
          <div className="space-y-4 text-gray-300 text-right" dir="rtl">
            <div className="bg-[#161836] p-4 rounded-lg border border-[#8B5CF6]/30">
              <h3 className="text-white font-bold mb-2 flex items-center justify-end gap-2">
                ملف google-services.json <span className="text-xl">🔥</span>
              </h3>
              <p className="text-sm leading-relaxed">
                هذا الملف هو صلة الوصل بين تطبيقك وقاعدة بيانات <strong className="text-[#D4AF37]">Firebase</strong>. بدونه لن يعمل تسجيل الدخول أو جلب الأماكن.
                <br/><br/>
                <strong>كيف تحصل عليه؟</strong><br/>
                1. اذهب إلى <a href="https://console.firebase.google.com/" className="text-[#8B5CF6] underline" target="_blank">Firebase Console</a> وأنشئ مشروعاً.<br/>
                2. أضف تطبيق Android واكتب اسم الحزمة: <code>com.nightout.algeria</code><br/>
                3. قم بتحميل ملف <code>google-services.json</code> وضعه داخل مجلد <code>app/</code> في مشروعك.
              </p>
            </div>

            <div className="bg-[#161836] p-4 rounded-lg border border-[#D4AF37]/30">
              <h3 className="text-white font-bold mb-2 flex items-center justify-end gap-2">
                مفتاح خريطة جوجل (MAPS_API_KEY) <span className="text-xl">🗺️</span>
              </h3>
              <p className="text-sm leading-relaxed">
                هذا المفتاح ضروري لظهور <strong className="text-[#D4AF37]">Google Maps</strong> في التطبيق.
                <br/><br/>
                <strong>كيف تحصل عليه؟</strong><br/>
                1. اذهب إلى <a href="https://console.cloud.google.com/" className="text-[#8B5CF6] underline" target="_blank">Google Cloud Console</a>.<br/>
                2. فعّل مكتبة <strong>Maps SDK for Android</strong>.<br/>
                3. من صفحة Credentials، أنشئ API Key جديد.<br/>
                4. افتح ملف <code>local.properties</code> في مشروعك وأضف السطر التالي:<br/>
                <code className="text-left block mt-2 text-green-400 bg-black/50 p-2 rounded" dir="ltr">MAPS_API_KEY=your_key_here</code>
              </p>
            </div>

            <div className="bg-[#161836] p-4 rounded-lg border border-white/20">
              <h3 className="text-white font-bold mb-2 flex items-center justify-end gap-2">
                لتشغيل بناء GitHub Actions (APK تلقائي) <span className="text-xl">⚙️</span>
              </h3>
              <p className="text-sm leading-relaxed">
                لكي تنجح عملية البناء على GitHub وتستخرج ملف APK، يجب إضافة الملفين كـ Secrets:
                <br/><br/>
                1. في مستودع GitHub، اذهب إلى <strong>Settings &gt; Secrets and variables &gt; Actions</strong>.<br/>
                2. أضف Secret باسم <code>MAPS_API_KEY</code> وضع فيه مفتاح الخرائط.<br/>
                3. أضف Secret باسم <code>GOOGLE_SERVICES_JSON</code> وانسخ بداخله <strong>محتوى</strong> ملف google-services.json بالكامل.
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
