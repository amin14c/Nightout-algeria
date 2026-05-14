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
